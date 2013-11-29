package com.manning.gia.sanitycheck.processing

import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

import com.manning.gia.sanitycheck.input.Expectations
import com.manning.gia.sanitycheck.input.TestSetReader
import com.manning.gia.sanitycheck.input.JsonTestSetReader

class JsonTestSetBuildBatchProcessor implements BuildBatchProcessor {
    Logger log = Logging.getLogger(JsonTestSetBuildBatchProcessor)
    BuildVerifier buildVerifier = new BuildVerifier()

    @Override
    void execute(File rootDir, String gradleVersion) {
        def testSets = parseTestSets()

        testSets.each { testSet ->
            testSet.projects.each { project ->
                File chapterDir = new File(rootDir, testSet.parentDir)
                File fullProjectDir = new File(chapterDir, project.dir)
                log.quiet "Testing build in directory '$fullProjectDir' with tasks $project.tasks"
                Expectations expectations = createExpectations(project)
                buildVerifier.verifySuccessfulExecution(fullProjectDir, gradleVersion, project.tasks as String[], project?.args as String[], expectations)
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

    private Expectations createExpectations(project) {
        Expectations expectations = new Expectations()
        String parsedExpectedResult = project?.expectations?.result

        if (parsedExpectedResult) {
            expectations.result = Expectations.Result.getResultForName(parsedExpectedResult)
        }

        expectations
    }
}