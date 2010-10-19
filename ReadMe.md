# HudsonGen

This project generates most of the Hudson configuration files for the FuseSource open source projects using [Scalate](http://scalate.fusesource.org/).

If you want to automatically create your Hudson configuration files for each of your projects using templates you might want to fork and adapt this project.

## Prerequisites

This build requires you install [Simple Build Tool](http://code.google.com/p/simple-build-tool/) then type the following

    sbt update
    
    
## Generating the configurations

To generate the configuration files run

    sbt someDir
        
Where someDir is the directory name where you want the Hudson configuration files generated.

Once you are happy with the look of your XML configuration files, you can run the HudsonGen build as a CI build on your Hudson machine.