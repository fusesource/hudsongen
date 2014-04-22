import java.io._

object Main extends Helper {

  protected def configure = {
    val platformsList = List("rhel", "aix", "solaris", "windows")
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
    
    add(Project("scalate-scala_next", new GitHub("scalate", "scalate"))).using { p =>
      p.git(_.branches("scala_next"))
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
    
    github("chirino", "resty-gwt").using { p => 
      p.mavenName("maven-3.0.2")
      p.checkin.maven.profiles = List("run-its")
      p.jdks("jdk6")
      p.builds = List(p.checkin, p.deploy)   
      p.ircNotify(IrcNotify("restygwt"), p.checkin, p.deploy)
    }
    github("koolio", "kool").using { p =>
      p.mavenName("maven-3.0.2")
      p.jdks("jdk6")
      p.deploy.timeout(50)
      p.ircNotify(IrcNotify("koolio"), p.checkin, p.deploy)
    }
    github("hawtio", "hawtio").using { p =>
      p.mavenName("maven-3.0.2")
      p.deploy.timeout(50)
      p.ircNotify(IrcNotify("hawtio"), p.checkin, p.deploy)
    }
    
    // ActiveMQ Apache Branches
    add(Project("activemq-trunk", new GitHub("apache", "activemq"))).using { p =>
      p.git(_.branches("trunk"))
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.timeout(15*60)
      p.mavenName("maven-3.0.5")
      p.checkin.maven.goals = List("-Dactivemq.tests=all")
      p.platform.maven.goals = List("-Dactivemq.tests=all")
      p.ircNotify(IrcNotify("activemq"), p.checkin, p.platform)
    }

    add(Project("activemq-apollo-trunk", new GitHub("apache", "activemq-apollo"))).using { p =>
      p.git(_.branches("trunk"))
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.timeout(2*60)
      p.mavenName("maven-3.0.5")
      p.checkin.maven.profiles = List("itests")
      p.platform.maven.profiles = List("itests")
      p.builds = List(p.checkin, p.platform)   
      p.ircNotify(IrcNotify("activemq"), p.checkin, p.platform)
    }

    // ActiveMQ Product Branches
    activemq("5.9.0.redhat-6-1-x-patch") using {
      p => p.jdks("jdk6", "jdk7","openjdk6","openjdk7");
        p.labels=platformsList;
        p.mavenName("maven-3.0.5")
    }

    activemq("5.9.0.redhat-6-1-x-stable") using {
      p => p.jdks("jdk6", "jdk7","openjdk6","openjdk7");
        p.labels=platformsList;
        p.mavenName("maven-3.0.5")
    }

    activemq("5.8.0.fuse-7-2-x-stable") using {
      p => p.jdks("jdk7", "jdk6");
      p.labels=platformsList;
      p.mavenName("maven-3.0.5")
    }
    activemq("5.7.0.fuse-7-1-x-stable") using { 
      p => p.jdks("jdk7", "jdk6"); 
      p.labels=platformsList; 
      p.mavenName("maven-3.0.5")
    }
    activemq("5.6.x-fuse").disable
    activemq("5.5.x-fuse").disable
    activemq("5.5.1.fuse-7-0-x-stable")
    activemq("5.5.1.fuse-7")
    activemq("5.5.1-fuse").disable
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

    camel("2.12.0.redhat-6-1-x-patch") using { p =>
      perfectus("camel", p)
      p.mavenName("maven-3.0.5")
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
    }

    camel("2.12.0.redhat-6-1-x-stable") using { p =>
      perfectus("camel", p)
      p.mavenName("maven-3.0.5")
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
    }

    camel("2.10.0.fuse-7-2-x-stable") using { p =>
      perfectus("camel", p)
      p.mavenName("maven-3.0.5")
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
    }
    camel("2.10.0.fuse-7-1-x-stable") using { p =>
      perfectus("camel", p)
      p.mavenName("maven-3.0.5")
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
    }
    camel("2.10.x-fuse") using { p =>
      perfectus("camel", p)
      p.mavenName("maven-3.0.2")
      p.disable
    }
    camel("2.9.0.fuse-7-0-x-stable") using { p =>
      perfectus("camel", p)
      p.mavenName("maven-3.0.2")
    }
    camel("2.9.x-fuse") using { p =>
      perfectus("camel", p)
      p.mavenName("maven-3.0.2")
      p.disable
    }
    camel("2.8.x-fuse") using { p =>
      perfectus("camel", p)
      p.mavenName("maven-3.0.2")
      p.disable
    }
    camel("2.7.x-fuse").disable
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
   
    cxf("2.7.x-fuse") using { p =>
       perfectus("cxf", p)
       p.mavenName("maven-3.0.2")
    }

    cxf("2.7.0.redhat-6-1-x-patch") using { p =>
      perfectus("cxf", p)
      p.mavenName("maven-3.0.5")
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
    }

    cxf("2.7.0.redhat-6-1-x-stable") using { p =>
      perfectus("cxf", p)
      p.mavenName("maven-3.0.5")
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
    }

    cxf("2.6.0.fuse-7-2-x-stable") using { p =>
       perfectus("cxf", p)
       p.mavenName("maven-3.0.5")
       p.jdks("jdk7", "jdk6")
       p.labels=platformsList
    }
    cxf("2.6.0.fuse-7-1-x-stable") using { p =>
       perfectus("cxf", p)
       p.mavenName("maven-3.0.5")
       p.jdks("jdk7", "jdk6")
       p.labels=platformsList
    }
    cxf("2.6.x-fuse") using { p =>
       perfectus("cxf", p)
       p.mavenName("maven-3.0.2")
       p.disable
    }
    cxf("2.5.0.fuse-7-0-x-stable") using { p =>
       perfectus("cxf", p)
       p.mavenName("maven-3.0.2")
    }
    cxf("2.2.x-fuse").disable
    cxf("2.2.6-fuse").disable
    cxf("2.3.x-fuse").disable
    cxf("2.4.x-fuse") using { p =>
      perfectus("cxf", p)
      p.disable
    }
    cxf("2.5.x-fuse") using { p =>
      perfectus("cxf", p)
      p.disable
    }
    cxf("2.4.2-fuse-00-xx") using {p =>
        perfectus("cxf", p)
        p.disable
    }
    
    //esbsystemtests
    esbsystemtests("kite-4.3.1") 
    esbsystemtests("kite-4.4.1") 
    
    // Karaf Branches
    karaf("karaf-2.0.0-fuse").disable
    karaf("karaf-2.1.x-fuse").disable
    karaf("karaf-2.2.x-fuse").disable
    karaf("karaf-2.2.5-fuse") using { p =>
      p.deploy.timeout(90)
      p.disable
    }
    karaf("2.2.5.fuse-7-0-x-stable") using { p =>
      p.deploy.timeout(90)
    }
    
    karaf("2.3.0.fuse-7-1-x-stable") using { p =>
      p.deploy.timeout(90)
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    karaf("2.3.0.fuse-7-2-x-stable") using { p =>
      p.deploy.timeout(90)
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    karaf("2.3.0.redhat-6-1-x-stable") using { p =>
      p.deploy.timeout(90)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    karaf("2.3.0.redhat-6-1-x-patch") using { p =>
      p.deploy.timeout(90)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    karaf("karaf-trunk-fuse") using ( perfectus("karaf", _) ) 
    
    // Felix components
    felix("4.4.1-fuse", "configadmin", "configadmin") using { p =>
      perfectus("felix-configadmin", p)
      p.disable
    }
    felix("4.4.1-fuse", "eventadmin/impl", "eventadmin") using { p =>
      perfectus("felix-eventadmin", p)
      p.disable
    }
    felix("4.4.1-fuse", "framework", "framework") using { p =>
      perfectus("felix-framework", p)
      p.disable
    }
    felix("4.4.1-fuse", "framework.security", "framework.security") using { p =>
      perfectus("felix-framework-security", p)
      p.disable
    }
    felix("4.4.1-fuse", "fileinstall", "fileinstall") using { p =>
      perfectus("felix-fileinstall", p)
      p.disable
    }
    felix("4.4.1-fuse", "webconsole", "webconsole") using { p =>
      perfectus("felix-webconsole", p)
      p.disable
    }

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
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("7.1.x.fuse-stable", "configadmin", "configadmin") using {  p => 
      perfectus("felix-configadmin", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("7.1.x.fuse-stable", "eventadmin/impl", "eventadmin") using {  p => 
      perfectus("felix-eventadmin", p) 
      p.jdks("jdk7", "jdk6")
      p.checkin.junitPublisher=None	// This build has no tests
      p.platform.junitPublisher=None
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("7.1.x.fuse-stable", "framework", "framework") using {  p => 
      perfectus("felix-framework", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("7.1.x.fuse-stable", "framework.security", "framework.security") using {  p => 
      perfectus("felix-framework-security", p) 
      p.jdks("jdk7", "jdk6")
      p.checkin.junitPublisher=None	// This build has no tests
      p.platform.junitPublisher=None
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("7.1.x.fuse-stable", "fileinstall", "fileinstall") using {  p => 
      perfectus("felix-fileinstall", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("7.1.x.fuse-stable", "gogo/runtime", "gogo") using {  p => 
      perfectus("felix-gogo", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("7.1.x.fuse-stable", "webconsole", "webconsole") using {  p => 
      perfectus("felix-webconsole", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    
    
    // 7.2.x.fuse-stable
    felix("7.2.x.fuse-stable", "utils", "utils") using {  p =>
      perfectus("felix-utils", p)
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("7.2.x.fuse-stable", "configadmin", "configadmin") using {  p => 
      perfectus("felix-configadmin", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("7.2.x.fuse-stable", "eventadmin/impl", "eventadmin") using {  p => 
      perfectus("felix-eventadmin", p) 
      p.jdks("jdk7", "jdk6")
      p.checkin.junitPublisher=None	// This build has no tests
      p.platform.junitPublisher=None
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("7.2.x.fuse-stable", "framework", "framework") using {  p => 
      perfectus("felix-framework", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("7.2.x.fuse-stable", "framework.security", "framework.security") using {  p => 
      perfectus("felix-framework-security", p) 
      p.jdks("jdk7", "jdk6")
      p.checkin.junitPublisher=None	// This build has no tests
      p.platform.junitPublisher=None
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("7.2.x.fuse-stable", "fileinstall", "fileinstall") using {  p => 
      perfectus("felix-fileinstall", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("7.2.x.fuse-stable", "gogo/runtime", "gogo") using {  p => 
      perfectus("felix-gogo", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("7.2.x.fuse-stable", "webconsole", "webconsole") using {  p => 
      perfectus("felix-webconsole", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    // 6.1.x.redhat-stable
    felix("6.1.x.redhat-stable", "utils", "utils") using {  p =>
      perfectus("felix-utils", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("6.1.x.redhat-stable", "configadmin", "configadmin") using {  p =>
      perfectus("felix-configadmin", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("6.1.x.redhat-stable", "eventadmin/impl", "eventadmin") using {  p =>
      perfectus("felix-eventadmin", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.checkin.junitPublisher=None	// This build has no tests
      p.platform.junitPublisher=None
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("6.1.x.redhat-stable", "framework", "framework") using {  p =>
      perfectus("felix-framework", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("6.1.x.redhat-stable", "framework.security", "framework.security") using {  p =>
      perfectus("felix-framework-security", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.checkin.junitPublisher=None	// This build has no tests
      p.platform.junitPublisher=None
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("6.1.x.redhat-stable", "fileinstall", "fileinstall") using {  p =>
      perfectus("felix-fileinstall", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("6.1.x.redhat-stable", "gogo/runtime", "gogo") using {  p =>
      perfectus("felix-gogo", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("6.1.x.redhat-stable", "scr", "scr") using {  p =>
      perfectus("felix-scr", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("6.1.x.redhat-stable", "webconsole", "webconsole") using {  p =>
      perfectus("felix-webconsole", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }


    // 6.1.x.redhat-patch
    felix("6.1.x.redhat-patch", "utils", "utils") using {  p =>
      perfectus("felix-utils", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("6.1.x.redhat-patch", "configadmin", "configadmin") using {  p =>
      perfectus("felix-configadmin", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("6.1.x.redhat-patch", "eventadmin/impl", "eventadmin") using {  p =>
      perfectus("felix-eventadmin", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.checkin.junitPublisher=None	// This build has no tests
      p.platform.junitPublisher=None
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("6.1.x.redhat-patch", "framework", "framework") using {  p =>
      perfectus("felix-framework", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("6.1.x.redhat-patch", "framework.security", "framework.security") using {  p =>
      perfectus("felix-framework-security", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.checkin.junitPublisher=None	// This build has no tests
      p.platform.junitPublisher=None
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("6.1.x.redhat-patch", "fileinstall", "fileinstall") using {  p =>
      perfectus("felix-fileinstall", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("6.1.x.redhat-patch", "gogo/runtime", "gogo") using {  p =>
      perfectus("felix-gogo", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("6.1.x.redhat-patch", "scr", "scr") using {  p =>
      perfectus("felix-scr", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    felix("6.1.x.redhat-patch", "webconsole", "webconsole") using {  p =>
      perfectus("felix-webconsole", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    felix("fuse-trunk", "configadmin", "configadmin")
    felix("fuse-trunk", "eventadmin/impl", "eventadmin")
    felix("fuse-trunk", "framework", "framework")
    felix("fuse-trunk", "fileinstall", "fileinstall")

	// Aries components
	aries("aries-0.3.x-fuse", "util", "util") using { p =>
    perfectus("aries-util",  p)
    p.disable
  }
	aries("aries-0.3.x-fuse", "blueprint", "blueprint") using { p =>
    perfectus("aries-blueprint", p)
    p.disable
  }
	aries("aries-0.3.x-fuse", "jmx", "jmx") using { p =>
    perfectus("aries-jmx", p)
    p.disable
  }
	aries("aries-0.3.x-fuse", "transaction", "transaction") using { p =>
    perfectus("aries-transaction", p)
    p.disable
  }
    aries("0.3.1.fuse-7-0-x-stable", "util", "util") using ( perfectus("aries-util", _) )
    aries("0.3.1.fuse-7-0-x-stable", "blueprint", "blueprint") using ( perfectus("aries-blueprint", _) )
    aries("0.3.1.fuse-7-0-x-stable", "jmx", "jmx") using ( perfectus("aries-jmx", _) )
    aries("0.3.1.fuse-7-0-x-stable", "transaction", "transaction") using ( perfectus("aries-transaction", _) )

    aries("1.0.0.fuse-7-1-x-stable", "util", "util") using {  p =>
      perfectus("aries-util", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    aries("1.0.0.fuse-7-1-x-stable", "blueprint", "blueprint") using { p =>
      perfectus("aries-blueprint", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    aries("1.0.0.fuse-7-1-x-stable", "jmx", "jmx") using {  p =>
      perfectus("aries-jmx", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    aries("1.0.0.fuse-7-1-x-stable", "transaction", "transaction") using {  p =>
      perfectus("aries-transaction", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
     }
    
    aries("1.0.1.fuse-7-2-x-stable", "util", "util") using {  p =>
      perfectus("aries-util", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    aries("1.0.1.fuse-7-2-x-stable", "blueprint", "blueprint") using { p =>
      perfectus("aries-blueprint", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    aries("1.0.1.fuse-7-2-x-stable", "jmx", "jmx") using {  p =>
      perfectus("aries-jmx", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    aries("1.0.1.fuse-7-2-x-stable", "transaction", "transaction") using {  p =>
      perfectus("aries-transaction", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
     }
    aries("1.0.1.fuse-7-2-x-stable", "proxy", "proxy") using {  p =>
      perfectus("aries-proxy", p)
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
     }


    aries("1.0.1.redhat-6-1-x-stable", "util", "util") using {  p =>
      perfectus("aries-util", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    aries("1.0.1.redhat-6-1-x-stable", "blueprint", "blueprint") using { p =>
      perfectus("aries-blueprint", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    aries("1.0.1.redhat-6-1-x-stable", "jmx", "jmx") using {  p =>
      perfectus("aries-jmx", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    aries("1.0.1.redhat-6-1-x-stable", "transaction", "transaction") using {  p =>
      perfectus("aries-transaction", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    aries("1.0.1.redhat-6-1-x-stable", "proxy", "proxy") using {  p =>
      perfectus("aries-proxy", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    aries("1.0.1.redhat-6-1-x-stable", "jpa", "jpa") using {  p =>
      perfectus("aries-jpa", p)
      p.jdks("jdk7", "jdk6","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    // 6.1 patch branches
    aries("1.0.1.redhat-6-1-x-patch", "util", "util") using {  p =>
      perfectus("aries-util", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    aries("1.0.1.redhat-6-1-x-patch", "blueprint", "blueprint") using { p =>
      perfectus("aries-blueprint", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    aries("1.0.1.redhat-6-1-x-patch", "jmx", "jmx") using {  p =>
      perfectus("aries-jmx", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    aries("1.0.1.redhat-6-1-x-patch", "transaction", "transaction") using {  p =>
      perfectus("aries-transaction", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    aries("1.0.1.redhat-6-1-x-patch", "proxy", "proxy") using {  p =>
      perfectus("aries-proxy", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    aries("1.0.1.redhat-6-1-x-patch", "jpa", "jpa") using {  p =>
      perfectus("aries-jpa", p)
      p.jdks("jdk7", "jdk6","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }


    // ServiceMix Branches
    smx4_nmr("trunk").disable
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
      p.disable
    }
    esb_nmr("nmr-1.5.2-fuse") using { p =>
      perfectus("nmr", p)
      p.mavenName("maven-3.0.2")
      p.disable
    }
    esb_nmr("nmr-1.6.0-fuse") using { p =>
      perfectus("nmr", p)
      p.mavenName("maven-3.0.2")
      p.disable
    }
    esb_nmr("1.6.0.fuse-7-0-x-stable") using { p =>
      perfectus("nmr", p)
      p.mavenName("maven-3.0.2")
    }
    esb_nmr("1.6.0.fuse-7-1-x-stable") using { p =>
      perfectus("nmr", p)
      p.mavenName("maven-3.0.2")
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    
    esb_nmr("1.6.0.fuse-7-2-x-stable") using { p =>
      perfectus("nmr", p)
      p.mavenName("maven-3.0.2")
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    esb_nmr("1.6.0.redhat-6-1-x-stable") using { p =>
      perfectus("nmr", p)
      p.mavenName("maven-3.0.2")
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    esb_nmr("1.6.0.redhat-6-1-x-patch") using { p =>
      perfectus("nmr", p)
      p.mavenName("maven-3.0.5")
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }


    smx4_features("trunk").disable
    smx4_features("features-4.2.0-fuse").disable
    smx4_features("features-4.3.0-fuse").disable
    esb_features("features-4.3.1-fuse").disable
    esb_features("features-4.4.0-fuse") using { p =>
      perfectus("smx4-features", p)
      p.disable
    }
    esb_features("features-4.4.1-fuse") using { p =>
      perfectus("smx4-features", p)
      p.disable
    }
    esb_features("features-5.0.0-fuse") using { p =>
      perfectus("smx4-features", p)
      p.disable
    }

    esb_features("4.5.0.fuse-7-0-x-stable") using { p =>
      perfectus("smx4-features", p)
      p.mavenName("maven-3.0.2")
    }
    esb_features("4.5.0.fuse-7-1-x-stable") using { p =>
      perfectus("smx4-features", p)
      p.mavenName("maven-3.0.5")
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
    }
    esb_features("4.5.0.fuse-7-2-x-stable") using { p =>
      perfectus("smx4-features", p)
      p.mavenName("maven-3.0.5")
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
    }

    esb_features("4.5.0.redhat-6-1-x-stable") using { p =>
      perfectus("smx4-features", p)
      p.mavenName("maven-3.0.5")
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
    }

    esb_features("4.5.0.redhat-6-1-x-patch") using { p =>
      perfectus("smx4-features", p)
      p.mavenName("maven-3.0.5")
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
    }


    fuseenterprise("fuseesb-7") using { p =>
      perfectus("fuseenterprise", p)
      p.mavenName("maven-3.0.2")
    }
    fuseenterprise("7.0.x.fuse-stable") using { p =>
      perfectus("fuseenterprise", p)
      p.mavenName("maven-3.0.2")
    }
    
    fuseenterprise("7.1.x.fuse-stable") using { p => 
      perfectus("fuseenterprise", p)
      p.mavenName("maven-3.0.5")
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
    }
    
    fuseenterprise("7.2.x.fuse-stable") using { p => 
      perfectus("fuseenterprise", p)
      p.mavenName("maven-3.0.5")
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
    }

    fuseenterprise("6.1.x.redhat-stable") using { p =>  
      // perfectus("fuseenterprise", p)
      p.mavenName("maven-3.0.5")
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
    }


    fuseenterprise("6.1.x.redhat-patch") using { p =>
    // perfectus("fuseenterprise", p)
      p.mavenName("maven-3.0.5")
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
    }



    smx_maven_plugins("trunk")
    smx_maven_plugins("maven-plugins-4.3.x-fuse")
    
    smx_utils("trunk").disable
    smx_utils("utils-1.3.0-fuse").disable
    esb_utils("utils-1.4.x-fuse").disable
    esb_utils("utils-1.5.0-fuse") using {p =>
        perfectus("smx-utils", p)
        p.disable
    }
    esb_utils("utils-1.5.1-fuse") using { p =>
      perfectus("smx-utils", p)
      p.disable
    }
    esb_utils("1.5.1.fuse-7-0-x-stable") using ( perfectus("smx-utils", _) )
    esb_utils("1.5.1.fuse-7-1-x-stable") using { p =>
      perfectus("smx-utils", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    esb_utils("1.5.1.fuse-7-2-x-stable") using { p =>
      perfectus("smx-utils", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    esb_utils("1.6.0.redhat-6-1-x-stable") using { p =>
      perfectus("smx-utils", p)
      p.jdks("jdk6", "jdk7", "openjdk6", "openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    esb_utils("1.6.0.redhat-6-1-x-checkin") using { p =>
      perfectus("smx-utils", p)
      p.jdks("jdk6", "jdk7", "openjdk6", "openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    esb_archetypes("2012.01.0.fuse-7-1-x-stable") using { p =>
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    
    esb_archetypes("2012.01.0.fuse-7-2-x-stable") using { p =>
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    esb_archetypes("2013.01.0.redhat-6-1-x-stable") using { p =>
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    esb_archetypes("2013.01.0.redhat-6-1-x-patch") using { p =>
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    
    esb_specs("specs-2.0.x-fuse") using { p =>
        perfectus("smx4-specs", p)
        p.disable
    }
    esb_specs("2.0.0.fuse-7-0-x-stable") using ( perfectus("smx4-specs", _) )
    esb_specs("2.0.0.fuse-7-1-x-stable") using { p =>
      perfectus("smx4-specs", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    
    esb_specs("2.0.0.fuse-7-2-x-stable") using { p =>
      perfectus("smx4-specs", p) 
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    esb_specs("2.3.0.redhat-6-1-x-stable") using { p =>
      perfectus("smx4-specs", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    esb_components("components-2009.01.x").disable
    esb_components("components-2010.01.0-fuse").disable
    esb_components("components-2010.02.0-fuse").disable
    esb_components("components-2011.01.0-fuse").disable
    esb_components("components-2011.02.0-fuse") using { p =>
        perfectus("smx-components", p)
        p.disable
    }
    esb_components("components-2011.02.1-fuse") using { p =>
      perfectus("smx-components", p)
      p.disable
    }
    esb_components("components-2012.01.0-fuse") using { p =>
      perfectus("smx-components", p)
      p.disable
    }
    esb_components("2012.01.0.fuse-7-0-x-stable") using ( perfectus("smx-components", _) )
    esb_components("2012.01.0.fuse-7-1-x-stable") using { p =>
      perfectus("smx-components", p)
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }
    
    esb_components("2012.01.0.fuse-7-2-x-stable") using { p =>
      perfectus("smx-components", p)
      p.jdks("jdk7", "jdk6")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    esb_components("2013.01.0.redhat-6-1-x-stable") using { p =>
      perfectus("smx-components", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    esb_components("2013.01.0.redhat-6-1-x-patch") using { p =>
      perfectus("smx-components", p)
      p.jdks("jdk6", "jdk7","openjdk6","openjdk7")
      p.labels=platformsList
      p.mavenName("maven-3.0.5")
    }

    // The specs don't have tests so don't need a nightly.
    subversion("smx4-specs-trunk-fuse", "http://fusesource.com/forge/svn/fuseesb/smx4/specs/trunk").removeBuild(_.platform).disable

    // ServiceMix 3
    servicemix3("3.3.1-fuse").timeout(2*60).disable
    servicemix3("3.4.0-fuse").timeout(2*60).disable
    servicemix3("3.5.0-fuse").timeout(2*60).disable
    servicemix3("3.6.0-fuse").timeout(2*60).disable

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
    project.timeout(15*60)
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
    project.timeout(180)
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
    case "trunk" => subversion("smx-maven-plugins-trunk-fuse", smx_base+"/maven-plugins/trunk").disable
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
