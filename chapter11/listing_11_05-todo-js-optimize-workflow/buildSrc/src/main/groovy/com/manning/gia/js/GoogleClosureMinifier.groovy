package com.manning.gia.js

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

class GoogleClosureMinifier extends DefaultTask {
    @InputFiles
    FileCollection inputFiles

    @OutputFile
    File outputFile

    @TaskAction
    void minify() {
        ant.taskdef(name: 'jscomp', classname: 'com.google.javascript.jscomp.ant.CompileTask', classpath: project.configurations.googleClosure.asPath)

        ant.jscomp(compilationLevel: 'simple', debug: 'false', output: outputFile.canonicalPath) {
            inputFiles.each { inputFile ->
                ant.sources(dir: inputFile.parent) {
                    ant.file(name: inputFile.name)
                }
            }
        }
    }
}
