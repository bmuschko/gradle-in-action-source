package com.manning.gia.js

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class Grunt extends DefaultTask {
    @Input
    List<String> commands

    @TaskAction
    void start() {
        project.exec {
            executable isWindows() ? 'grunt.cmd' : '/usr/local/lib/node_modules/grunt-cli/bin/grunt'
            args commands
        }
    }

    boolean isWindows() {
        System.properties['os.name'].toLowerCase().contains('windows')
    }
}