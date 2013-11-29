package com.manning.gia.sanitycheck.processing

import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection
import org.gradle.tooling.BuildLauncher
import org.gradle.tooling.model.Model
import org.gradle.tooling.model.GradleProject

import com.manning.gia.sanitycheck.output.BuildResult

class BuildRunner {
    BuildResult executeBuildScript(File projectDir, String gradleVersion, String[] tasks, String[] args) {
        GradleConnector connector = GradleConnector.newConnector()
        ProjectConnection connection

        try {
            connection = connector.useGradleVersion(gradleVersion).forProjectDirectory(projectDir).connect()
            BuildLauncher buildLauncher = connection.newBuild()
            buildLauncher.forTasks(tasks)

            if (args) {
                buildLauncher.withArguments(args)
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream()
            buildLauncher.setStandardOutput(stream).run()
            return new BuildResult(output: stream.toString('UTF-8'))
        }
        finally {
            connection?.close()
        }
    }
}