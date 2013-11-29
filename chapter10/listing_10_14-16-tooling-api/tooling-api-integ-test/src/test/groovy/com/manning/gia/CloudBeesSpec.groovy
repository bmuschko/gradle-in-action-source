package com.manning.gia

import spock.lang.Specification
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection
import org.gradle.tooling.BuildLauncher
import org.gradle.tooling.model.Model
import org.gradle.tooling.model.GradleProject

class CloudBeesSpec extends Specification {
    static final String GRADLE_VERSION = '1.5'
    static final File PROJECT_DIR = new File('../script-under-test')

    def "CloudBees application information is rendered on the command line"() {
        given:
        String[] tasks = ['cloudBeesAppInfo'] as String[]
        String arguments = '-PappId=gradle-in-action/todo'
        when:
        ByteArrayOutputStream stream = executeWithGradleConnector(GRADLE_VERSION, PROJECT_DIR, tasks, arguments)
        String output = stream.toString('UTF-8')
        then:
        output != null
        output.contains('Application id : gradle-in-action/todo')
        output.contains('title : todo')
        output.contains('urls : [todo.gradle-in-action.cloudbees.net]')
    }

    private ByteArrayOutputStream executeWithGradleConnector(String gradleVersion, File projectDir, String[] tasks, String arguments) {
        GradleConnector connector = GradleConnector.newConnector()
        ProjectConnection connection

        try {
            connection = connector.useGradleVersion(gradleVersion).forProjectDirectory(projectDir).connect()
            BuildLauncher buildLauncher = connection.newBuild()
            buildLauncher.forTasks(tasks).withArguments(arguments)
            ByteArrayOutputStream stream = new ByteArrayOutputStream()
            buildLauncher.setStandardOutput(stream).run()
            return stream
        }
        finally {
            connection.close()
        }
    }
}