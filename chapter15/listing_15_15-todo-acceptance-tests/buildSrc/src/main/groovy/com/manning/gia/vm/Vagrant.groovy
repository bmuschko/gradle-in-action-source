package com.manning.gia.vm

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class Vagrant extends DefaultTask {
    static final String VAGRANT_EXECUTABLE = 'vagrant'

    @Input
    List<String> commands

    @Input
    File dir

    @TaskAction
    void runCommand() {
        commands.add(0, VAGRANT_EXECUTABLE)
        logger.info "Executing Vagrant command: '${commands.join(' ')}'"

        def process = commands.execute(null, dir)
        process.consumeProcessOutput(System.out, System.err)
        process.waitFor()

        if (process.exitValue() != 0) {
            throw new GradleException()
        }
    }
}