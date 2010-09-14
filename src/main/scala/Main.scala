import java.io._
import Helper._

object Main {
  def main(args:Array[String]):Unit = {
    if( args.length < 1 ) {
      println("Expected the jobs directory as an argument")
      System.exit(1)
    }
    jobs_dir = new File(args(0))
    
    // Random Support Projects
    generate(github("scalate", "scalate"))
    generate(github("chirino", "hawtdb"))
    generate(github("chirino", "hawtdispatch"))
    generate(github("chirino", "hawtjni"))
    generate(github("chirino", "hawtbuf"))
    generate(github("chirino", "mvnplugins"))
    generate(github("chirino", "rmiviajms"))
    generate(github("chirino", "jansi"))
    generate(github("gnodet", "wikitext").git(_.branches("origin")))
    
    generate(forge_git("jansi"))
    generate(forge_git("console"))
    generate(forge_git("mop"))

    // ActiveMQ Branches
    generate(activemq("trunk-fusesource").timeout(2*60))
    generate(activemq("5.4-fusesource").timeout(2*60))
    generate(subversion("activemq-5.3.1-fuse", "http://fusesource.com/forge/svn/fusemq/branches/activemq-5.3.1-fuse").timeout(2*60))

    // Camel Branches
    generate(subversion("camel-trunk-fuse", "http://fusesource.com/forge/svn/fuseeip/trunk").timeout(3*60))
    generate(subversion("camel-2.2.0-fuse", "http://fusesource.com/forge/svn/fuseeip/branches/camel-2.2.0-fuse").timeout(3*60))
    generate(subversion("camel-1.x-fuse", "http://fusesource.com/forge/svn/fuseeip/branches/camel-1.x-fuse").timeout(3*60))

    // CXF Branches
    generate(subversion("cxf-trunk-fuse", "http://fusesource.com/forge/svn/fusesf/trunk"))
    generate(subversion("cxf-2.2.x-fuse", "http://fusesource.com/forge/svn/fusesf/branches/cxf-2.2.x-fuse"))

    // ServiceMix Branches
    generate(smx4_nmr("trunk"))
    generate(smx4_nmr("nmr-1.2.0-fuse"))
    generate(smx4_nmr("nmr-1.3.0-fuse"))

    generate(smx4_features("trunk"))
    generate(smx4_features("features-4.2.0-fuse"))
    generate(smx4_features("features-4.3.0-fuse"))
    
    generate(smx_maven_plugins("trunk"))
    generate(smx_maven_plugins("maven-plugins-4.3.0-fusesource"))
    
    generate(smx_utils("trunk"))

    generate(smx_components("trunk").timeout(2*60))
    generate(smx_components("components-2010.02.0-fuse").timeout(2*60))
    generate(smx_components("components-2010.01.0-fuse").timeout(2*60))
    generate(smx_components("components-2009.01.x").timeout(2*60))

    // The specks don't have tests do don't need a nightly.
    var specs = subversion("smx4-specs-trunk-fuse", "http://fusesource.com/forge/svn/fuseesb/smx4/specs/trunk")
    checkin(specs)
    deploy(specs)

  }
  
  def activemq(branch:String) = new Project("activemq-"+branch, new Git("ssh://git@forge.fusesource.com/activemq.git", None, List(branch)))

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