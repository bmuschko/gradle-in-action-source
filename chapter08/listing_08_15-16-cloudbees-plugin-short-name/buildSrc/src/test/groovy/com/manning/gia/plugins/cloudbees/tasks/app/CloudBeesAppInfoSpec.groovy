package com.manning.gia.plugins.cloudbees.tasks.app

import spock.lang.Specification
import org.gradle.api.*
import org.gradle.api.plugins.*
import org.gradle.testfixtures.ProjectBuilder

class CloudBeesAppInfoSpec extends Specification {
    static final TASK_NAME = 'cloudBeesAppInfo'
    Project project

    def setup() {
        project = ProjectBuilder.builder().build()
    }

    def "Adds app info task"() {
        expect:
        project.tasks.findByName(TASK_NAME) == null
        when:
        project.task(TASK_NAME, type: CloudBeesAppInfo) {
            appId = 'gradle-in-action/todo'
            apiKey = 'myKey'
            secret = 'mySecret'
        }
        then:
        Task task = project.tasks.findByName(TASK_NAME)
        task != null
        task.description == 'Returns the basic information about an application.'
        task.group == 'CloudBees'
        task.apiFormat == 'xml'
        task.apiVersion == '1.0'
        task.apiUrl == 'https://api.cloudbees.com/api'
        task.appId == 'gradle-in-action/todo'
        task.apiKey == 'myKey'
        task.secret == 'mySecret'
    }

    def "Executes app info task with wrong credentials"() {
        expect:
        project.tasks.findByName(TASK_NAME) == null
        when:
        Task task = project.task(TASK_NAME, type: CloudBeesAppInfo) {
            appId = 'gradle-in-action/todo'
            apiKey = 'myKey'
            secret = 'mySecret'
        }

        task.start()
        then:
        project.tasks.findByName(TASK_NAME) != null
        thrown(GradleException)
    }

    def "Executes app info task with correct credentials"() {
        expect:
        project.tasks.findByName(TASK_NAME) == null
        Properties gradleProperties = getGradleProperties()
        gradleProperties['cloudbeesApiKey'] != null
        gradleProperties['cloudbeesApiSecret'] != null
        when:
        Task task = project.task(TASK_NAME, type: CloudBeesAppInfo) {
            appId = 'gradle-in-action/todo'
            apiKey = gradleProperties['cloudbeesApiKey']
            secret = gradleProperties['cloudbeesApiSecret']
        }

        task.start()
        then:
        project.tasks.findByName(TASK_NAME) != null
    }

    private Properties getGradleProperties() {
        def props = new Properties()
        File gradlePropertiesFile = new File(System.getProperty('user.home'), '.gradle/gradle.properties')

        if (gradlePropertiesFile.exists()) {
            gradlePropertiesFile.withInputStream {
                stream -> props.load(stream)
            }
        }

        addPropertyFromEnvVariable(props, 'cloudbeesApiKey')
        addPropertyFromEnvVariable(props, 'cloudbeesApiSecret')
        props
    }

    private void addPropertyFromEnvVariable(Properties props, String key) {
        if (!props.containsKey(key)) {
            def env = System.getenv()
            String gradleEnvPropKey = "ORG_GRADLE_PROJECT_$key"

            if (env.containsKey(gradleEnvPropKey)) {
                props[key] = env[gradleEnvPropKey]
            }
        }
    }
}