package com.hoang.mavenplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo( name = "goodbye")
public class GoodbyeMojo extends AbstractMojo {
    public void execute() throws MojoExecutionException   {
        getLog().info( "Goodbye my love, see you again." );
    }
}
