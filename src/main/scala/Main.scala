import java.io._

object Main extends Helper {

  protected def configure = {
    // FuseForge Projects
    forge_git("jansi")
    // forge_git("console")
    forge_git("insight").using{ p =>
      p.deploy.maven.profiles = List("distro")
    }
    
    forge_git("fmc")
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

    github("fusesource", "fuse").using { p =>
      p.mavenName("maven-3.0.2")
      p.jdks("jdk6")
      p.checkin.maven.profiles = List("distro", "itests")
      p.deploy.maven.profiles = List("distro")
      p.platform.maven.profiles = List("distro")
      p.deploy.timeout(50)
      p.ircNotify(IrcNotify("fusefabric"), p.checkin, p.deploy)
    }

    github("fusesource", "hawtdb")
    github("fusesource", "hawtdispatch")
    github("fusesource", "hawtjni")
    github("fusesource", "hawtbuf")
    github("fusesource", "mvnplugins")
    github("fusesource", "rmiviajms")
    github("fusesource", "jansi")
    github("fusesource", "brew")
    github("fusesource", "jclouds").using { p =>
      p.mavenName("maven-3.0.2")
      p.git(_.branches("OSGi"))
      p.deploy.timeout(60)
    }
    
    github("koolio", "kool").using { p =>
      p.mavenName("maven-3.0.2")
      p.jdks("jdk6")
      p.deploy.timeout(50)
      p.ircNotify(IrcNotify("koolio"), p.checkin, p.deploy)
    }
    

    // ActiveMQ Branches
    activemq("trunk-fuse")
    activemq("5.6.1.fuse-7-1-x-stable") using { p => p.jdks("jdk7", "jdk6")}
    activemq("5.6.x-fuse")
    activemq("5.5.x-fuse")
    activemq("5.5.1.fuse-7-0-x-stable")
    activemq("5.5.1.fuse-7")
    activemq("5.5.1-fuse")
    activemq("5.4.x-fuse") using { p =>
      perfectus("activemq", p)
      p.disable
    }

    // no 5.3.x releases planned right now.
    // subversion("activemq-5.3.1-fuse", "http://fusesource.com/forge/svn/fusemq/branches/activemq-5.3.1-fuse") using { p=>
    //   p.timeout(4*60)
    //   p.deploy.timeout(60)
    // }

    // Apollo Branches
    apollo("trunk-fuse")

    // Camel Branches
    camel("trunk-fuse") using { p =>
      perfectus("camel", p)
      p.mavenName("maven-3.0.2")
    }
    camel("2.10.0.fuse-7-1-x-stable") using { p =>
      perfectus("camel", p)
      p.mavenName("maven-3.0.2")
      p.jdks("jdk7", "jdk6")
    }
    camel("2.10.x-fuse") using { p =>
      perfectus("camel", p)
      p.mavenName("maven-3.0.2")
    }
    camel("2.9.0.fuse-7-0-x-stable") using { p =>
      perfectus("camel", p)
      p.mavenName("maven-3.0.2")
    }
    camel("2.9.x-fuse") using { p =>
      perfectus("camel", p)
      p.mavenName("maven-3.0.2")
    }
    camel("2.8.x-fuse") using { p =>
      perfectus("camel", p)
      p.mavenName("maven-3.0.2")
    }
    camel("2.7.x-fuse")
    camel("2.6.x-fuse").disable
    camel("2.4.x-fuse").disable
    camel("2.2.x-fuse").disable 
    camel("1.x-fuse") using { p =>
      perfectus("camel", p)
      p.disable
    }

    // CXF Branches
    cxf("trunk-fuse") using { p =>
       perfectus("cxf", p)
       p.mavenName("maven-3.0.2")
    }
    cxf("2.6.0.fuse-7-1-x-stable") using { p =>
       perfectus("cxf", p)
       p.mavenName("maven-3.0.2")
       p.jdks("jdk7", "jdk6")
    }
    cxf("2.6.x-fuse") using { p =>
       perfectus("cxf", p)
       p.mavenName("maven-3.0.2")
    }
    cxf("2.5.0.fuse-7-0-x-stable") using { p =>
       perfectus("cxf", p)
       p.mavenName("maven-3.0.2")
    }
    cxf("2.2.x-fuse").disable
    cxf("2.2.6-fuse").disable
    cxf("2.3.x-fuse")
    cxf("2.4.x-fuse") using ( perfectus("cxf", _) ) 
    cxf("2.5.x-fuse") using ( perfectus("cxf", _) )
    cxf("2.4.2-fuse-00-xx") using ( perfectus("cxf", _) ) 
    
    //esbsystemtests
    esbsystemtests("kite-4.3.1") 
    esbsystemtests("kite-4.4.1") 
    
    // Karaf Branches
    karaf("karaf-2.0.0-fuse").disable
    karaf("karaf-2.1.x-fuse").disable
    karaf("karaf-2.2.x-fuse")
    karaf("karaf-2.2.5-fuse") using { p =>
      p.deploy.timeout(90)
    }
    karaf("2.2.5.fuse-7-0-x-stable") using { p =>
      p.deploy.timeout(90)
    }
    
    karaf("2.3.0.fuse-7-1-x-stable") using { p =>
      p.deploy.timeout(90)
      p.jdks("jdk7", "jdk6")
    }
    karaf("karaf-trunk-fuse") using ( perfectus("karaf", _) ) 
    
    // Felix components
    felix("4.4.1-fuse", "configadmin", "configadmin") using ( perfectus("felix-configadmin", _) )
    felix("4.4.1-fuse", "eventadmin/impl", "eventadmin") using ( perfectus("felix-eventadmin", _) )
    felix("4.4.1-fuse", "framework", "framework") using ( perfectus("felix-framework", _) )
    felix("4.4.1-fuse", "framework.security", "framework.security") using ( perfectus("felix-framework-security", _) )
    felix("4.4.1-fuse", "fileinstall", "fileinstall") using ( perfectus("felix-fileinstall", _) )
    felix("4.4.1-fuse", "webconsole", "webconsole") using ( perfectus("felix-webconsole", _) )

    felix("7.0.x-fuse", "configadmin", "configadmin")
    felix("7.0.x-fuse", "eventadmin/impl", "eventadmin")
    felix("7.0.x-fuse", "framework", "framework")
    felix("7.0.x-fuse", "framework.security", "framework.security") using ( perfectus("felix-framework-security", _) )
    felix("7.0.x-fuse", "fileinstall", "fileinstall")
    felix("7.0.x-fuse", "gogo/runtime", "gogo")

    felix("7.0.x.fuse-stable", "configadmin", "configadmin") using ( perfectus("felix-configadmin", _) )
    felix("7.0.x.fuse-stable", "eventadmin/impl", "eventadmin") using ( perfectus("felix-eventadmin", _) )
    felix("7.0.x.fuse-stable", "framework", "framework") using ( perfectus("felix-framework", _) )
    felix("7.0.x.fuse-stable", "framework.security", "framework.security") using ( perfectus("felix-framework-security", _) )
    felix("7.0.x.fuse-stable", "fileinstall", "fileinstall") using ( perfectus("felix-fileinstall", _) )
    felix("7.0.x.fuse-stable", "gogo/runtime", "gogo") using ( perfectus("felix-gogo", _) )
    felix("7.0.x.fuse-stable", "webconsole", "webconsole") using ( perfectus("felix-webconsole", _) )

    felix("7.1.x.fuse-stable", "utils", "utils") using {  p =>
      perfectus("felix-utils", p)
      p.jdks("jdk7", "jdk6")
    }
    felix("7.1.x.fuse-stable", "configadmin", "configadmin") using {  p => 
      perfectus("felix-configadmin", p) 
      p.jdks("jdk7", "jdk6")
    }
    felix("7.1.x.fuse-stable", "eventadmin/impl", "eventadmin") using {  p => 
      perfectus("felix-eventadmin", p) 
      p.jdks("jdk7", "jdk6")
    }
    felix("7.1.x.fuse-stable", "framework", "framework") using {  p => 
      perfectus("felix-framework", p) 
      p.jdks("jdk7", "jdk6")
    }
    felix("7.1.x.fuse-stable", "framework.security", "framework.security") using {  p => 
      perfectus("felix-framework-security", p) 
      p.jdks("jdk7", "jdk6")
    }
    felix("7.1.x.fuse-stable", "fileinstall", "fileinstall") using {  p => 
      perfectus("felix-fileinstall", p) 
      p.jdks("jdk7", "jdk6")
    }
    felix("7.1.x.fuse-stable", "gogo/runtime", "gogo") using {  p => 
      perfectus("felix-gogo", p) 
      p.jdks("jdk7", "jdk6")
    }
    felix("7.1.x.fuse-stable", "webconsole", "webconsole") using {  p => 
      perfectus("felix-webconsole", p) 
      p.jdks("jdk7", "jdk6")
    }

    felix("fuse-trunk", "configadmin", "configadmin")
    felix("fuse-trunk", "eventadmin/impl", "eventadmin")
    felix("fuse-trunk", "framework", "framework")
    felix("fuse-trunk", "fileinstall", "fileinstall")

	// Aries components
	aries("aries-0.3.x-fuse", "util", "util") using ( perfectus("aries-util", _) )
	aries("aries-0.3.x-fuse", "blueprint", "blueprint") using ( perfectus("aries-blueprint", _) )
	aries("aries-0.3.x-fuse", "jmx", "jmx") using ( perfectus("aries-jmx", _) )
	aries("aries-0.3.x-fuse", "transaction", "transaction") using ( perfectus("aries-transaction", _) )

        aries("0.3.1.fuse-7-0-x-stable", "util", "util") using ( perfectus("aries-util", _) )
        aries("0.3.1.fuse-7-0-x-stable", "blueprint", "blueprint") using ( perfectus("aries-blueprint", _) )
        aries("0.3.1.fuse-7-0-x-stable", "jmx", "jmx") using ( perfectus("aries-jmx", _) )
        aries("0.3.1.fuse-7-0-x-stable", "transaction", "transaction") using ( perfectus("aries-transaction", _) )

        aries("1.0.0.fuse-7-1-x-stable", "util", "util") using {  p =>
          perfectus("aries-util", p) 
          p.jdks("jdk7", "jdk6")
        }
        aries("1.0.0.fuse-7-1-x-stable", "blueprint", "blueprint") using { p =>
          perfectus("aries-blueprint", p) 
          p.jdks("jdk7", "jdk6")
    	}
        aries("1.0.0.fuse-7-1-x-stable", "jmx", "jmx") using {  p =>
          perfectus("aries-jmx", p) 
          p.jdks("jdk7", "jdk6")
        }
        aries("1.0.0.fuse-7-1-x-stable", "transaction", "transaction") using {  p =>
          perfectus("aries-transaction", p) 
          p.jdks("jdk7", "jdk6")
         }

    // ServiceMix Branches
    smx4_nmr("trunk")
    smx4_nmr("nmr-1.2.0-fuse").disable
    smx4_nmr("nmr-1.3.0-fuse").disable
    esb_nmr("nmr-1.4.x-fuse").disable
    esb_nmr("nmr-1.5.0-fuse") using { p =>
      perfectus("nmr", p)
      p.mavenName("maven-3.0.2")
      p.disable
    }
    esb_nmr("nmr-1.5.1-fuse") using { p =>
      perfectus("nmr", p)
      p.mavenName("maven-3.0.2")
    }
    esb_nmr("nmr-1.5.2-fuse") using { p =>
      perfectus("nmr", p)
      p.mavenName("maven-3.0.2")
    }
    esb_nmr("nmr-1.6.0-fuse") using { p =>
      perfectus("nmr", p)
      p.mavenName("maven-3.0.2")
    }
    esb_nmr("1.6.0.fuse-7-0-x-stable") using { p =>
      perfectus("nmr", p)
      p.mavenName("maven-3.0.2")
    }
    esb_nmr("1.6.0.fuse-7-1-x-stable") using { p =>
      perfectus("nmr", p)
      p.mavenName("maven-3.0.2")
      p.jdks("jdk7", "jdk6")
    }

    smx4_features("trunk")
    smx4_features("features-4.2.0-fuse").disable
    smx4_features("features-4.3.0-fuse").disable
    esb_features("features-4.3.1-fuse") 
    esb_features("features-4.4.0-fuse") using ( perfectus("smx4-features", _) ) 
    esb_features("features-4.4.1-fuse") using ( perfectus("smx4-features", _) )    
    esb_features("features-5.0.0-fuse") using { p =>
      perfectus("smx4-features", p)
      p.mavenName("maven-3.0.2")
    }
    esb_features("4.5.0.fuse-7-0-x-stable") using { p =>
      perfectus("smx4-features", p)
      p.mavenName("maven-3.0.2")
    }
    esb_features("4.5.0.fuse-7-1-x-stable") using { p =>
      perfectus("smx4-features", p)
      p.mavenName("maven-3.0.2")
      p.jdks("jdk7", "jdk6")
    }

    fuseenterprise("fuseesb-7") using { p =>
      perfectus("fuseenterprise", p)
      p.mavenName("maven-3.0.2")
    }
    fuseenterprise("7.0.x.fuse-stable") using { p =>
      perfectus("fuseenterprise", p)
      p.mavenName("maven-3.0.2")
    }
    
    fuseenterprise("master") using { p =>  // For "7.1.x.fuse-stable"
      perfectus("fuseenterprise", p)
      p.mavenName("maven-3.0.2")
      p.jdks("jdk7", "jdk6")
    }

    smx_maven_plugins("trunk")
    smx_maven_plugins("maven-plugins-4.3.x-fuse")
    
    smx_utils("trunk")
    smx_utils("utils-1.3.0-fuse").disable
    esb_utils("utils-1.4.x-fuse").disable
    esb_utils("utils-1.5.0-fuse") using ( perfectus("smx-utils", _) ) 
    esb_utils("utils-1.5.1-fuse") using ( perfectus("smx-utils", _) ) 
    esb_utils("1.5.1.fuse-7-0-x-stable") using ( perfectus("smx-utils", _) )
    esb_utils("1.5.1.fuse-7-1-x-stable") using { p =>
      perfectus("smx-utils", p) 
      p.jdks("jdk7", "jdk6")
    }

    esb_archetypes("2012.01.0.fuse-7-1-x-stable") using { p =>
      p.jdks("jdk7", "jdk6")
    }
    
    esb_specs("specs-2.0.x-fuse") using ( perfectus("smx4-specs", _) )
    esb_specs("2.0.0.fuse-7-0-x-stable") using ( perfectus("smx4-specs", _) )
    esb_specs("2.0.0.fuse-7-1-x-stable") using { p =>
      perfectus("smx4-specs", p) 
      p.jdks("jdk7", "jdk6")
    }

    esb_components("components-2009.01.x").disable
    esb_components("components-2010.01.0-fuse").disable
    esb_components("components-2010.02.0-fuse").disable
    esb_components("components-2011.01.0-fuse").disable
    esb_components("components-2011.02.0-fuse") using ( perfectus("smx-components", _) ) 
    esb_components("components-2011.02.1-fuse") using ( perfectus("smx-components", _) )
    esb_components("components-2012.01.0-fuse") using ( perfectus("smx-components", _) )
    esb_components("2012.01.0.fuse-7-0-x-stable") using ( perfectus("smx-components", _) )
    esb_components("2012.01.0.fuse-7-1-x-stable") using { p =>
      perfectus("smx-components", p)
      p.jdks("jdk7", "jdk6")
    }

    // The specs don't have tests so don't need a nightly.
    subversion("smx4-specs-trunk-fuse", "http://fusesource.com/forge/svn/fuseesb/smx4/specs/trunk").removeBuild(_.platform) 

    // ServiceMix 3
    servicemix3("3.3.1-fuse").timeout(2*60).disable
    servicemix3("3.4.0-fuse").timeout(2*60).disable
    servicemix3("3.5.0-fuse").timeout(2*60).disable
    servicemix3("3.6.0-fuse").timeout(2*60)

    fusecdc("master")
  }
  
  def apollo(branch:String) =  {
    val project = new Project("activemq-apollo-"+branch, new Git("ssh://git@forge.fusesource.com/apollo.git", None, List(branch)))
    project.timeout(1*60)
    project.deploy.timeout(60)
    project.mavenName("maven-3.0.2")
    add(project)
  }

  def fusecdc(branch:String) =  {
    val project = new Project("fusecdc-"+branch, new Git("ssh://git@forge.fusesource.com/fusecdc.git", None, List(branch)))
    project.timeout(1*60)
    project.deploy.timeout(60)
    project.mavenName("maven-3.0.2")
    add(project)
  }

  def activemq(branch:String) =  {
    val project = new Project("activemq-"+branch, new Git("ssh://git@forge.fusesource.com/activemq.git", None, List(branch)))
    project.timeout(10*60)
    project.deploy.timeout(60)
    project.mavenName("maven-3.0.2")
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
    val project = new Project("smx4-features-" + branch, 
                              new Git("ssh://git@forge.fusesource.com/esbfeatures.git", None, List(branch)))
    project.deploy.timeout(90)
    project.timeout(90)
    add(project)
  }

  def fuseenterprise(branch: String):Project = {
    val repoUrl = branch match {
      case "master" => "git@github.com:fusesource/fuse.git"
      case _ => "ssh://git@forge.fusesource.com/fuseenterprise.git"
    }
    //val project = new Project("fuseenterprise-" + branch, new Git("ssh://git@forge.fusesource.com/fuseenterprise.git", None, List(branch)))
    val project = new Project("fuseenterprise-" + branch, new Git(repoUrl, None, List(branch)))
    project.deploy.timeout(90)
    project.timeout(90)
    add(project)
  }
  
  def esb_archetypes(branch: String) =
    add(new Project("archetypes-" + branch, 
                    new Git("ssh://git@forge.fusesource.com/esbarchetypes.git", None, List(branch))))

                    
  /*
   * Starting with FUSE ESB JBI Components 2011.01.0-fuse, branches are being maintained in this git repository
   * instead of the old svn location.
   */  
  def esb_components(branch: String) = {
    val project = new Project("smx-components-" + branch, 
                              new Git("ssh://git@forge.fusesource.com/esbcomponents.git", None, List(branch)))
    project.timeout(2*60)
    add(project)
  }
  
  /*
   * Starting with FUSE ESB NMR 1.3.1-fuse, branches are being maintained in this git repository
   * instead of the old svn location.
   */
  def esb_nmr(branch: String) =
    add(new Project("smx4-nmr-" + branch, 
                    new Git("ssh://git@forge.fusesource.com/esbnmr.git", None, List(branch))))

  /*
   * Starting with FUSE ESB Utils 1.3.1-fuse, branches are being maintained in this git repository
   * instead of the old svn location.
   */
  def esb_utils(branch: String) =
    add(new Project("smx-utils-" + branch, 
                    new Git("ssh://git@forge.fusesource.com/esbutils.git", None, List(branch))))                    
  
  def esb_specs(branch: String) =
    add(new Project("smx4-specs-" + branch,
                    new Git("ssh://git@forge.fusesource.com/esbspecs.git", None, List(branch))))

  def karaf(branch:String) = 
    add(new Project("karaf-" + branch, new Git("ssh://git@forge.fusesource.com/karaf.git", None, List(branch))))

  def felix(branch:String, comp:String, name:String) = {
    val project = new Project("felix-" + name + "-" + branch,
					          new Git("ssh://git@forge.fusesource.com/felix.git", None, List(branch)))
	project.builds.foreach(_.maven.rootPom = comp + "/pom.xml")
	project.perfectus_tests.maven.rootPom = comp + "/pom.xml"
	project.mavenName("maven-3.0.2")
	add(project)
  }

  def aries(branch:String, comp:String, name:String) = {
    val project = new Project("aries-" + name + "-" + branch,
					          new Git("ssh://git@forge.fusesource.com/aries.git", None, List(branch)))
	project.builds.foreach(_.maven.rootPom = comp + "/pom.xml")
	project.perfectus_tests.maven.rootPom = comp + "/pom.xml"
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
