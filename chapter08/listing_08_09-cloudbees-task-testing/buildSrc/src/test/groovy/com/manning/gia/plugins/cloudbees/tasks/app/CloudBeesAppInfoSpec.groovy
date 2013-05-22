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
			gradleProperties['cloudbees.api.key'] != null
			gradleProperties['cloudbees.api.secret'] != null
		when:
			Task task = project.task(TASK_NAME, type: CloudBeesAppInfo) {
				appId = 'gradle-in-action/todo'
                apiKey = gradleProperties['cloudbees.api.key']
				secret = gradleProperties['cloudbees.api.secret']
			}

			task.start()
		then:
		    project.tasks.findByName(TASK_NAME) != null
	}
	
	private Properties getGradleProperties() {
		def props = new Properties()

		new File(System.getProperty('user.home'), '.gradle/gradle.properties').withInputStream { 
			stream -> props.load(stream) 
		}
		
		props
	}
}