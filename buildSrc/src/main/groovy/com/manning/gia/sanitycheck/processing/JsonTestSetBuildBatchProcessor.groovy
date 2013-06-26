package com.manning.gia.sanitycheck.processing

import com.manning.gia.sanitycheck.input.TestSetReader
import com.manning.gia.sanitycheck.input.JsonTestSetReader

class JsonTestSetBuildBatchProcessor implements BuildBatchProcessor {
    BuildVerifier buildVerifier = new BuildVerifier()

    @Override
    void execute(File rootDir, String gradleVersion) {
        def testSets = parseTestSets()

        testSets.each { testSet ->
            testSet.projects.each { project ->
                File chapterDir = new File(rootDir, testSet.parentDir)
                File fullProjectDir = new File(chapterDir, project.dir)
                buildVerifier.verifySuccessfulExecution(fullProjectDir, gradleVersion, project.tasks as String[])
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