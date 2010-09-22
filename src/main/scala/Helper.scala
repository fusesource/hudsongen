import collection.mutable.ListBuffer
import java.io._
import org.fusesource.scalate.{TemplateEngine, DefaultRenderContext, Binding}
import org.fusesource.scalate.support.FileResourceLoader
import org.fusesource.scalate.util.IOUtil._

trait SCM {
  def url:String
  def web_url:Option[String]
  
}

class Git(var url:String, var web_url: Option[String] = None, var branches: List[String] = List("master")) extends SCM {
  def branches(values:String*): this.type = { branches = List(values: _*); this}
  def web_url(value:Option[String]): this.type = { web_url = value; this}
}

class GitHub(var user:String, var project:String) extends Git("git://github.com/"+user+"/"+project+".git", Some("http://github.com/"+user+"/"+project+""))

class Subversion(val url:String, val local:String) extends SCM {
  var web_url:Option[String] = Some(url) 
  def web_url(value:Option[String]): this.type = { web_url = value; this}
}

case class MavenOptions(profiles: List[String] = Nil) {

  /**
   * Returns the command line argument for profiles
   */
  def goalsArguments = profiles.map("-P" + _).mkString(" ")
}

case class Build(name: String) {
  var mavenOptions = MavenOptions()
  def mavenOptions(value: MavenOptions): this.type = { mavenOptions = value; this }
}

case class Project(val name:String, val scm:SCM) {
  
  var jdks = List("jdk6")
  def jdks(values:String*): this.type = { jdks = List(values: _*); this}
  
  var labels = List("ubuntu","windows")
  def labels(values:String*): this.type = { labels = List(values: _*); this}
  
  var timeout = 60
  def timeout(value:Int): this.type = { timeout = value; this}

  def git(proc: (Git)=>Unit): this.type = { proc(scm.asInstanceOf[Git]); this }


  // Helper methods

  /**
   * Lets the project be configured using a block which avoids global variables
   * needing to be defined for each project, instead its a local scoped parameter
   */
  def using(f: Project => Unit): this.type = {
    f(this)
    this
  }

  // builds
  val checkin = Build("checkin")
  val platform = Build("platform")
  val deploy = Build("deploy")

  var builds: List[Build] = List(checkin, platform, deploy)

  def removeBuild(b: Build): this.type =  {
    builds = builds filterNot(_ == b)
    this
  }

  def removeBuild(f: Project => Build): this.type =
    removeBuild(f(this))

  def removeBuilds(list: List[Build]): this.type =  {
    builds = builds filterNot(list contains)
    this
  }

  def removeBuilds(f: Project => List[Build]): this.type =
    removeBuilds(f(this))
}

object Helper {
  def with_trailing_slash(value:String) = {
    if( value.endsWith("/") ) {
      value
    } else {
      value + "/"
    }
  }
}

abstract class Helper {
  
  var jobs_dir:File = _

  val projects = ListBuffer[Project]()

  def add(project: Project): Project = {
    projects += project
    project
  }

  /**
   * Configures the model
   */
  protected def configure: Unit


  /**
   * Runs the command line shell
   */
  def main(args: Array[String]): Unit = {
    // TODO should we use Karaf annotations for this stuff?
    if (args.length < 1) {
      println("Expected the jobs directory as an argument")
      System.exit(1)
    }
    jobs_dir = new File(args(0))
    configure

    generate
  }


  /////////////////////////////////////////////////////////////////////
  // Config Generators
  /////////////////////////////////////////////////////////////////////

  def subversion(project:String, url:String) = {
    add(Project(project, new Subversion(url, project)))
  }

  def github(user:String, project:String) = {
    add(Project(project, new GitHub(user, project)))
  }

  def forge_git(project:String) = {
    add(new Project(project, new Git("ssh://git@forge.fusesource.com/"+project+".git")))
  }

  def generate(): Unit = {
    for (project <- projects) {
      generate(project)
    }
  }

  def generate(project:Project): Unit = {
    for (b <- project.builds) {
      build(project, b)
    }
  }
  
  def build(project:Project, build: Build): Unit = {
    val name = build.name
    job(project.name + "-" + name, render(name + ".jade", Map("project" -> project, "build" -> build)))
  }

  /////////////////////////////////////////////////////////////////////
  // IO Helpers
  /////////////////////////////////////////////////////////////////////
  private def job(name:String, config:String) = {
    write(jobs_dir/name/"config.xml", config)
  }
  
  private def write(file:File, value:Any) = {
    file.getParentFile.mkdirs
    writeText(file, value.toString)
  } 
  
  /////////////////////////////////////////////////////////////////////
  // Scalate Helpers
  /////////////////////////////////////////////////////////////////////
  
  org.fusesource.scalate.scaml.ScamlOptions.autoclose = null
  private val engine = new TemplateEngine
  engine.resourceLoader = new FileResourceLoader(Some(new File("./src/main/template")))
  engine.workingDirectory = new File("./target/scalate")
  engine.bindings ++= List(Binding("val project: Project"), Binding("var build: Build"))

  private def render( template:String, attributes: Map[String,Any]) = {
    val buffer = new StringWriter()
    val context = new DefaultRenderContext(null, engine, new PrintWriter(buffer))
    for ((key, value) <- attributes) {
      context.attributes(key) = value
    }
    context.include(template, false)
    buffer.toString
  }

}