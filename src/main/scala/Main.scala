import java.io._

object Main extends Helper {

  protected def configure = {
    // FuseForge Projects
    forge_git("jansi")
    // forge_git("console")
    forge_git("insight").using{ p =>
      p.deploy.maven.profiles = List("distro")
    }
    
    forge_git("fon")
    forge_git("mop")
    forge_git("ridersource")

    github("fusesource", "wikitext").git(_.branches("origin"))

    github("scalate", "scalate").using { p =>
      p.mavenName("maven-3.0.2")
      // Run the build on jdk 5 and 6 since the core should be JDK 1.5 compatible,
      // but we also want to test the java 6 features like Jersey integration.
      p.jdks("jdk6", "jdk5")
      p.checkin.maven.profiles = List("m2", "distro")
      p.deploy.maven.profiles = List("m2", "distro")
      p.platform.maven.profiles = List("m2", "distro")
      p.deploy.timeout(45)
      p.ircNotify(IrcNotify("scalate"), p.checkin, p.deploy)
    }

    github("fusesource", "fabric").using { p =>
      p.mavenName("maven-3.0.2")
      p.jdks("jdk6")
      p.checkin.maven.profiles = List("distro", "itests")
      p.deploy.maven.profiles = List("distro")
      p.platform.maven.profiles = List("distro")
      p.deploy.timeout(45)
      p.ircNotify(IrcNotify("fusefabric"), p.checkin, p.deploy)
    }

    github("fusesource", "hawtdb")
    github("fusesource", "hawtdispatch")
    github("fusesource", "hawtjni")
    github("fusesource", "hawtbuf")
    github("fusesource", "mvnplugins")
    github("fusesource", "rmiviajms")
    github("fusesource", "jansi")
    github("fusesource", "jclouds").using { p =>
      p.mavenName("maven-3.0.2")
      p.git(_.branches("OSGi"))
    }

    // ActiveMQ Branches
    activemq("trunk-fuse")
    activemq("5.5.x-fuse")
    activemq("5.4.x-fuse") using ( perfectus("activemq", _) ) 

    // no 5.3.x releases planned right now.
    // subversion("activemq-5.3.1-fuse", "http://fusesource.com/forge/svn/fusemq/branches/activemq-5.3.1-fuse") using { p=>
    //   p.timeout(4*60)
    //   p.deploy.timeout(60)
    // }

    // Apollo Branches
    apollo("trunk-fuse")

    // Camel Branches
    camel("trunk-fuse")
    camel("2.8.x-fuse")
    camel("2.7.x-fuse")
    camel("2.6.x-fuse")
    camel("2.4.x-fuse") 
    camel("2.2.x-fuse") 
    camel("1.x-fuse") using ( perfectus("camel", _) ) 

    // CXF Branches
    cxf("trunk-fuse") 
    cxf("2.2.x-fuse")
    cxf("2.2.6-fuse")
    cxf("2.3.x-fuse")
    cxf("2.4.x-fuse") using ( perfectus("cxf", _) ) 
    
    //esbsystemtests
    esbsystemtests("kite-4.3.1") 
    
    // Karaf Branches
    karaf("karaf-2.0.0-fuse")
    karaf("karaf-2.1.x-fuse")
    karaf("karaf-2.2.x-fuse")
    karaf("karaf-trunk-fuse") using ( perfectus("karaf", _) ) 
    
    // Felix components
    felix("fuse-trunk", "configadmin", "configadmin") using ( perfectus("felix-configadmin", _) )
    felix("fuse-trunk", "eventadmin/impl", "eventadmin") using ( perfectus("felix-eventadmin", _) )
    felix("fuse-trunk", "framework", "framework") using ( perfectus("felix-framework", _) )
    felix("fuse-trunk", "fileinstall", "fileinstall") using ( perfectus("felix-fileinstall", _) )

    // ServiceMix Branches
    smx4_nmr("trunk")
    smx4_nmr("nmr-1.2.0-fuse")
    smx4_nmr("nmr-1.3.0-fuse")
    esb_nmr("nmr-1.4.x-fuse") 
    esb_nmr("nmr-1.5.0-fuse") using { p =>
      perfectus("nmr", p)
      p.mavenName("maven-3.0.2")
    }
    esb_nmr("nmr-1.5.1-fuse") using { p =>
      perfectus("nmr", p)
      p.mavenName("maven-3.0.2")
    }

    smx4_features("trunk")
    smx4_features("features-4.2.0-fuse")
    smx4_features("features-4.3.0-fuse") 
    esb_features("features-4.3.1-fuse") 
    esb_features("features-4.4.0-fuse") using ( perfectus("smx4-features", _) ) 
    esb_features("features-4.4.1-fuse") using ( perfectus("smx4-features", _) )    

    smx_maven_plugins("trunk")
    smx_maven_plugins("maven-plugins-4.3.0-fuse")
    
    smx_utils("trunk")
    smx_utils("utils-1.3.0-fuse")
    esb_utils("utils-1.4.x-fuse") 
    esb_utils("utils-1.5.x-fuse") using ( perfectus("smx-utils", _) ) 

    esb_components("components-2009.01.x")
    esb_components("components-2010.01.0-fuse")
    esb_components("components-2010.02.0-fuse")
    esb_components("components-2011.01.0-fuse") 
    esb_components("components-2011.02.0-fuse") using ( perfectus("smx-components", _) ) 
    esb_components("components-2011.02.1-fuse") using ( perfectus("smx-components", _) )

    // The specs don't have tests so don't need a nightly.
    subversion("smx4-specs-trunk-fuse", "http://fusesource.com/forge/svn/fuseesb/smx4/specs/trunk").removeBuild(_.platform) 

    // ServiceMix 3
    servicemix3("3.3.1-fuse").timeout(2*60)    
    servicemix3("3.4.0-fuse").timeout(2*60)
    servicemix3("3.5.0-fuse").timeout(2*60)
    servicemix3("3.6.0-fuse").timeout(2*60)
  }
  
  def apollo(branch:String) =  {
    val project = new Project("activemq-apollo-"+branch, new Git("ssh://git@forge.fusesource.com/apollo.git", None, List(branch)))
    project.timeout(1*60)
    project.deploy.timeout(60)
    project.mavenName("maven-3.0.2")
    add(project)
  }

  def activemq(branch:String) =  {
    val project = new Project("activemq-"+branch, new Git("ssh://git@forge.fusesource.com/activemq.git", None, List(branch)))
    project.timeout(6*60)
    project.deploy.timeout(60)
    add(project)
  }
    
    
  def camel(branch: String) = {
    val project = new Project("camel-"+branch, new Git("ssh://git@forge.fusesource.com/camel.git", None, List(branch)))
    project.timeout(8*60)
    project.deploy.timeout(4*60)
    add(project)
  }
  
  def cxf(branch: String) = {
    val project = new Project("cxf-"+branch, new Git("ssh://git@forge.fusesource.com/cxf.git", None, List(branch)))
    project.timeout(3*60)
    project.deploy.timeout(3*60)
    project.deploy.maven.profiles = List("everything", "jaxws22")
    add(project)
  }
 
  def esbsystemtests(branch: String) = {
    val project = new Project("esbsystemtests-"+branch, new Git("ssh://git@forge.fusesource.com/esbsystemtests.git", None, List(branch)))
    project.timeout(2*60)
    project.checkin.maven.profiles = List("ci")
    project.deploy.maven.profiles = List("ci")
    project.platform.maven.profiles = List("ci")
    add(project)
  }
 
  /*
   * Starting with FUSE ESB 4.3.1-fuse, branches are being maintained in this git repository
   * instead of the old svn location.
   */  
  def esb_features(branch: String) = {
    val project = new Project("smx4-" + branch, 
                              new Git("ssh://git@forge.fusesource.com/esbfeatures.git", None, List(branch)))
    project.deploy.timeout(90)
    project.timeout(90)
    add(project)
  }    
  
  /*
   * Starting with FUSE ESB JBI Components 2011.01.0-fuse, branches are being maintained in this git repository
   * instead of the old svn location.
   */  
  def esb_components(branch: String) = {
    val project = new Project("smx-" + branch, 
                              new Git("ssh://git@forge.fusesource.com/esbcomponents.git", None, List(branch)))
    project.timeout(2*60)
    add(project)
  }
  
  /*
   * Starting with FUSE ESB NMR 1.3.1-fuse, branches are being maintained in this git repository
   * instead of the old svn location.
   */
  def esb_nmr(branch: String) =
    add(new Project("smx4-" + branch, 
                    new Git("ssh://git@forge.fusesource.com/esbnmr.git", None, List(branch))))

  /*
   * Starting with FUSE ESB Utils 1.3.1-fuse, branches are being maintained in this git repository
   * instead of the old svn location.
   */
  def esb_utils(branch: String) =
    add(new Project("smx-" + branch, 
                    new Git("ssh://git@forge.fusesource.com/esbutils.git", None, List(branch))))                    
  
  def karaf(branch:String) = 
    add(new Project(branch, new Git("ssh://git@forge.fusesource.com/karaf.git", None, List(branch))))

  def felix(branch:String, comp:String, name:String) = {
    val project = new Project("felix-" + name + "-" + branch,
					          new Git("ssh://git@forge.fusesource.com/fuseosgi.git", None, List(branch)))
	project.builds.foreach(_.maven.rootPom = comp + "/pom.xml")
	project.perfectus_tests.maven.rootPom = comp + "/pom.xml"
	project.removeBuild(_.platform)
	add(project)
  }

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

  def smx3(version:String) = subversion("smx-" + version, smx_base + "/branches/servicemix-" + version)
  
  def servicemix3(version: String) =     
    add(new Project("smx-" + version, 
                    new Git("ssh://git@forge.fusesource.com/servicemix3.git", None, List("servicemix-" + version)))) 
}
