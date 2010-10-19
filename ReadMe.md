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