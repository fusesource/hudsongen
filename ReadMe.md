# HudsonGen

This project generates most of the Hudson configuration files for the [FuseSource](http://fusesource.com/) [open source projects](http://fusesource.com/forge) using the [Scalate template engine](http://scalate.fusesource.org/).

If you want to automatically create your Hudson configuration files for each of your projects using templates you might want to fork and adapt this project.

Note that you can always have custom Hudson builds outside of the configurations that this tool generates; this tool aims to just auto-generate the common builds for your projects where the CI builds are very similar across individual projects or to reuse the same builds on different branches on the same project etc.

## Prerequisites

This build requires you install [Simple Build Tool](http://code.google.com/p/simple-build-tool/) then type the following

    sbt update
    
    
## Generating the configurations

To generate the configuration files type the following into a console

    sbt 
    run someDir
        
Where someDir is the directory name where you want the Hudson configuration files generated.

Once you are happy with the look of your XML configuration files, you can run the HudsonGen build as a CI build on your Hudson machine.

For example our CI build for the HudsonGen project looks like this (so Hudson reloads itself after a new build)

    /opt/sbt-0.7.4/sbt update
    /opt/sbt-0.7.4/sbt run /var/lib/hudson/jobs
    /var/lib/hudson/reload
    
    
## How it works

You define all the various projects from github or FuseForge in the [Main.scala](http://github.com/fusesource/hudsongen/blob/master/src/main/scala/Main.scala) file which defines all the metadata for your builds.

The actual implementation detail of using Scalate templates is all done in [Helper.scala](http://github.com/fusesource/hudsongen/blob/master/src/main/scala/Helper.scala) and the [templates are here](http://github.com/fusesource/hudsongen/tree/master/src/main/template/).

For further details see [Scalate Templates](http://scalate.fusesource.org/)