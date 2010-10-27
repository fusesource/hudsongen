import java.io._

object Main extends Helper {

  protected def configure = {
    // Random Support Projects
    github("scalate", "scalate").using{ p =>
      p.checkin.maven.profiles = List("download")
      p.deploy.maven.profiles = List("download", "distro")
    }

    github("chirino", "hawtdb")
    github("chirino", "hawtdispatch")
    github("chirino", "hawtjni")
    github("chirino", "hawtbuf")
    github("chirino", "mvnplugins")
    github("chirino", "rmiviajms")
    github("chirino", "jansi")
    github("gnodet", "wikitext").git(_.branches("origin"))
    
    forge_git("jansi")
    // forge_git("console")
    forge_git("mop")

    // ActiveMQ Branches
    activemq("trunk-fusesource").timeout(4*60)
    activemq("5.4-fusesource").timeout(4*60)
    subversion("activemq-5.3.1-fuse", "http://fusesource.com/forge/svn/fusemq/branches/activemq-5.3.1-fuse").timeout(4*60)

    // Camel Branches
    subversion("camel-trunk-fuse", "http://fusesource.com/forge/svn/fuseeip/trunk").timeout(5*60)
    subversion("camel-2.2.0-fuse", "http://fusesource.com/forge/svn/fuseeip/branches/camel-2.2.0-fuse").timeout(5*60)
    subversion("camel-1.x-fuse", "http://fusesource.com/forge/svn/fuseeip/branches/camel-1.x-fuse").timeout(5*60)

    // CXF Branches
    subversion("cxf-trunk-fuse", "http://fusesource.com/forge/svn/fusesf/trunk").timeout(2*60)
    subversion("cxf-2.2.x-fuse", "http://fusesource.com/forge/svn/fusesf/branches/cxf-2.2.x-fuse").timeout(2*60)
    
    // Karaf Branches
    karaf("karaf-2.0.0-fuse")

    // ServiceMix Branches
    smx4_nmr("trunk")
    smx4_nmr("nmr-1.2.0-fuse")
    smx4_nmr("nmr-1.3.0-fuse")

    smx4_features("trunk").timeout(1*90)
    smx4_features("features-4.2.0-fuse").timeout(1*90)
    smx4_features("features-4.3.0-fuse").timeout(1*90)
    
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
  
  def karaf(branch:String) = 
    add(new Project("karaf-"+branch, new Git("ssh://git@forge.fusesource.com/karaf.git", None, List(branch))))

  val smx_base = "http://fusesource.com/forge/svn/fuseesb"
  def smx4_nmr(branch:String) =  branch match {
    case "trunk" => subversion("smx4-nmr-trunk-fuse", smx_base+"/smx4/nmr/trunk")
    case branch => subversion("smx4-"+branch, smx_base+"/smx4/nmr/branches/"+branch)
  }

  def smx4_features(branch:String) =  branch match {
    case "trunk" => subversion("smx4-features-trunk-fuse", smx_base+"/smx4/features/trunk")
    case branch => subversion("smx4-"+branch, smx_base+"/smx4/features/branches/"+branch)
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