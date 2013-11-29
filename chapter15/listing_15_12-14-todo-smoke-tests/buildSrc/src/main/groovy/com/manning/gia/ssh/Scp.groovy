package com.manning.gia.ssh

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.*

class Scp extends DefaultTask {
    @InputFiles
    FileCollection classpath

    @InputFile
    File sourceFile

    @Input
    String destination

    @Input
    File keyFile

    @Input
    Integer port

    @TaskAction
    void runCommand() {
        logger.quiet "Copying file '$sourceFile' to server."
        ant.taskdef(name: 'jschScp', classname: 'org.apache.tools.ant.taskdefs.optional.ssh.Scp', classpath: classpath.asPath)
        ant.jschScp(file: sourceFile, todir: destination, keyfile: keyFile.canonicalPath, port: port, trust: 'true')
    }
}