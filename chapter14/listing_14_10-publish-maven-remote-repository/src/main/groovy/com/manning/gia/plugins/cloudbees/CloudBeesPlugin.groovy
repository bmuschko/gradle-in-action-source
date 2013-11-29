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
        project.tasks.withType(CloudBeesTask).whenTaskAdded { CloudBeesTask task ->
            def extension = project.extensions.findByName(EXTENSION_NAME)
            task.conventionMapping.apiUrl = { extension.apiUrl }
            task.conventionMapping.apiKey = { extension.apiKey }
            task.conventionMapping.secret = { extension.secret }
        }

        addAppTasks(project)
    }

    private void addAppTasks(Project project) {
        project.tasks.withType(CloudBeesAppInfo).whenTaskAdded { task ->
            task.conventionMapping.appId = { getAppId(project) }
        }

        project.task('cloudBeesAppInfo', type: CloudBeesAppInfo)

        project.tasks.withType(CloudBeesAppDeployWar).whenTaskAdded { task ->
            task.conventionMapping.appId = { getAppId(project) }
            task.conventionMapping.message = { project.hasProperty('message') ? project.message : null }
            task.conventionMapping.warFile = { getWarFile(project) }
        }

        project.task('cloudBeesAppDeployWar', type: CloudBeesAppDeployWar)
    }

    private String getAppId(Project project) {
        project.hasProperty('appId') ? project.appId : project.extensions.findByName(EXTENSION_NAME).appId
    }

    private File getWarFile(Project project) {
        project.hasProperty('warFile') ? new File(project.getProperty('warFile')) : project.tasks.getByName(WarPlugin.WAR_TASK_NAME).archivePath
    }
}