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

class Project(val name:String, val scm:SCM) {
  
  var jdks = List("jdk6")
  def jdks(values:String*): this.type = { jdks = List(values: _*); this}
  
  var labels = List("ubuntu","windows")
  def labels(values:String*): this.type = { labels = List(values: _*); this}
  
  var timeout = 60
  def timeout(value:Int): this.type = { timeout = value; this}

  def git(proc: (Git)=>Unit): this.type = { proc(scm.asInstanceOf[Git]); this}
  
}

object Helper {
  
  var jobs_dir:File = _
  
  /////////////////////////////////////////////////////////////////////
  // Config Generators
  /////////////////////////////////////////////////////////////////////

  def subversion(project:String, url:String) = {
    new Project(project, new Subversion(url, project))
  }

  def github(user:String, project:String) = {
    new Project(project, new GitHub(user, project))
  }

  def forge_git(project:String) = {
    new Project(project, new Git("ssh://git@forge.fusesource.com/"+project+".git"))
  }

  def generate(project:Project) = {
    checkin(project)
    platform(project)
    deploy(project)
  }
  
  def checkin(project:Project) = {
    job(project.name+"-checkin", render("checkin.jade", Map("project"->project)))
  }
  def platform(project:Project) = {
    job(project.name+"-platform", render("platform.jade", Map("project"->project)))
  }
  def deploy(project:Project) = {
    job(project.name+"-deploy", render("deploy.jade", Map("project"->project)))
  }

  def with_trailing_slash(value:String) = {
    if( value.endsWith("/") ) {
      value
    } else {
      value + "/"
    }
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
  engine.bindings ::= Binding("val project: Project")
  
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