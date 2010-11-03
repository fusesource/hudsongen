import java.io._

object Main extends Helper {

  protected def configure = {
    // FuseForge Projects
    forge_git("jansi")
    forge_git("console")
    forge_git("mop")
    forge_git("ridersource")

    github("fusesource", "wikitext").git(_.branches("origin"))

    github("scalate", "scalate").mavenName("maven-3.0").using{ p =>
      p.checkin.maven.profiles = List("download")
      p.deploy.maven.profiles = List("download", "distro")
      p.deploy.timeout(45)
    }

    github("chirino .", "hawtdb")
    github("chirino", "hawtdispatch")
    github("chirino", "hawtjni")
    github("chirino", "hawtbuf")
    github("chirino", "mvnplugins")
    github("chirino", "rmiviajms")
    github("chirino", "jansi")
    
    // ActiveMQ Branches
    activemq("trunk-fusesource").timeout(4*60)
    activemq("5.4-fusesource").timeout(4*60)
    subversion("activemq-5.3.1-fuse", "http://fusesource.com/forge/svn/fusemq/branches/activemq-5.3.1-fuse").timeout(4*60)

    // Camel Branches
    camel("camel-trunk-fuse", "http://fusesource.com/forge/svn/fuseeip/trunk")
    camel("camel-2.2.0-fuse", "http://fusesource.com/forge/svn/fuseeip/branches/camel-2.2.0-fuse")
    camel("camel-1.x-fuse", "http://fusesource.com/forge/svn/fuseeip/branches/camel-1.x-fuse")

    // CXF Branches
    cxf("cxf-trunk-fuse", "http://fusesource.com/forge/svn/fusesf/trunk")
    cxf("cxf-2.2.x-fuse", "http://fusesource.com/forge/svn/fusesf/branches/cxf-2.2.x-fuse")
    
    // Karaf Branches
    karaf("karaf-2.0.0-fuse")

    // ServiceMix Branches
    smx4_nmr("trunk")
    smx4_nmr("nmr-1.2.0-fuse")
    smx4_nmr("nmr-1.3.0-fuse")

    smx4_features("trunk")
    smx4_features("features-4.2.0-fuse")
    smx4_features("features-4.3.0-fuse")
    
    smx_maven_plugins("trunk")
    smx_maven_plugins("maven-plugins-4.3.0-fuse")
    
    smx_utils("trunk")

    smx_components("trunk").timeout(2*60)
    smx_components("components-2010.02.0-fuse").timeout(2*60)
    smx_components("components-2010.01.0-fuse").timeout(2*60)
    smx_components("components-2009.01.x").timeout(2*60)

    // The specs don't have tests so don't need a nightly.
    subversion("smx4-specs-trunk-fuse", "http://fusesource.com/forge/svn/fuseesb/smx4/specs/trunk").removeBuild(_.platform)
  }
  
  def activemq(branch:String) = 
    add(new Project("activemq-"+branch, new Git("ssh://git@forge.fusesource.com/activemq.git", None, List(branch))))
    
  def camel(id: String, source: String) = {
    val project = subversion(id, source).timeout(8*60)
    project.deploy.timeout(4*60)
    project
  }
  
  def cxf(id: String, source: String) = {
    subversion(id, source).using { project =>
      project.timeout(2*60)
      project.deploy.timeout(90)
    }
  }
  
  def karaf(branch:String) = 
    add(new Project("karaf-"+branch, new Git("ssh://git@forge.fusesource.com/karaf.git", None, List(branch))))

  val smx_base = "http://fusesource.com/forge/svn/fuseesb"
  def smx4_nmr(branch:String) =  branch match {
    case "trunk" => subversion("smx4-nmr-trunk-fuse", smx_base+"/smx4/nmr/trunk")
    case branch => subversion("smx4-"+branch, smx_base+"/smx4/nmr/branches/"+branch)
  }

  def smx4_features(branch:String) = {
    val project = branch match {
      case "trunk" => subversion("smx4-features-trunk-fuse", smx_base+"/smx4/features/trunk")
      case branch => subversion("smx4-"+branch, smx_base+"/smx4/features/branches/"+branch)
    }
    project.deploy.timeout(90)
    project.timeout(90)
  }
  
  def smx_maven_plugins(branch:String) =  branch match {
    case "trunk" => subversion("smx-maven-plugins-trunk-fuse", smx_base+"/maven-plugins/trunk")
    case branch => subversion("smx-"+branch, smx_base+"/maven-plugins/branches/"+branch)
  }
  
  def smx_utils(branch:String) =  branch match {
    case "trunk" => subversion("smx-utils-trunk-fuse", smx_base+"/utils/trunk")
    case branch => subversion("smx-"+branch, smx_base+"/utils/branches/"+branch)
  }

  def smx_components(branch:String) =  branch match {
    case "trunk" => subversion("smx-components-trunk-fuse", smx_base+"/components/trunk")
    case branch => subversion("smx-"+branch, smx_base+"/components/branches/"+branch)
  }

}