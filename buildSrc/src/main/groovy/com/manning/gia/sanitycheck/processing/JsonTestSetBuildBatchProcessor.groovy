package com.manning.gia.sanitycheck.processing

import groovyx.gpars.GParsPool
import groovy.util.logging.Slf4j

import com.manning.gia.sanitycheck.input.TestSetReader
import com.manning.gia.sanitycheck.input.JsonTestSetReader

@Slf4j
class JsonTestSetBuildBatchProcessor implements BuildBatchProcessor {
    BuildVerifier buildVerifier = new BuildVerifier()

    @Override
    void execute(File rootDir, String gradleVersion) {
        def testSets = parseTestSets()

        GParsPool.withPool {
            testSets.eachParallel { testSet ->
                testSet.projects.each { project ->
                    File chapterDir = new File(rootDir, testSet.parentDir)
                    File fullProjectDir = new File(chapterDir, project.dir)
                    log.info "Testing build in directory '$fullProjectDir' with tasks $project.tasks"
                    buildVerifier.verifySuccessfulExecution(fullProjectDir, gradleVersion, project.tasks as String[], project?.args as String[])
                }
            }
        }
    }

    def parseTestSets() {
        TestSetReader testSetReader = new JsonTestSetReader()
        def chapterFiles = testSetReader.parse(getClass().getClassLoader().getResource('chapters.json').newReader('UTF-8'))

        chapterFiles.collect { chapterFile ->
            testSetReader.parse(getClass().getClassLoader().getResource(chapterFile).newReader('UTF-8'))
        }
    }
}