package com.manning.gia.plugins.cloudbees

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.WarPlugin
import com.manning.gia.plugins.cloudbees.tasks.CloudBeesTask
import com.manning.gia.plugins.cloudbees.tasks.app.CloudBeesAppInfo
import com.manning.gia.plugins.cloudbees.tasks.app.CloudBeesAppDeployWar

class CloudBeesPlugin implements Plugin<Project> {
    static final String EXTENSION_NAME = 'cloudBees'

    void apply(Project project) {
        project.plugins.apply(WarPlugin)
        project.extensions.create(EXTENSION_NAME, CloudBeesPluginExtension)
        addTasks(project)
    }

    private void addTasks(Project project) {
        project.tasks.withType(CloudBeesTask) {
            def extension = project.extensions.findByName(EXTENSION_NAME)
            conventionMapping.apiUrl = { extension.apiUrl }
            conventionMapping.apiKey = { extension.apiKey }
            conventionMapping.secret = { extension.secret }
        }

        addAppTasks(project)
    }

    private void addAppTasks(Project project) {
        project.task('cloudBeesAppInfo', type: CloudBeesAppInfo) {
            conventionMapping.appId = { getAppId(project) }
        }

        project.task('cloudBeesAppDeployWar', type: CloudBeesAppDeployWar) {
            conventionMapping.appId = { getAppId(project) }
            conventionMapping.message = { project.hasProperty('message') ? project.message : null }
            conventionMapping.warFile = { getWarFile(project) }
        }
    }

    private String getAppId(Project project) {
        project.hasProperty('appId') ? project.appId : project.extensions.findByName(EXTENSION_NAME).appId
    }

    private File getWarFile(Project project) {
        project.hasProperty('warFile') ? new File(project.getProperty('warFile')) : project.tasks.getByName(WarPlugin.WAR_TASK_NAME).archivePath
    }
}