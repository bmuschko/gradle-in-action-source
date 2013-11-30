package com.manning.gia.plugins.cloudbees

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.WarPlugin
import com.manning.gia.plugins.cloudbees.tasks.CloudBeesTask
import com.manning.gia.plugins.cloudbees.tasks.app.CloudBeesAppInfo
import com.manning.gia.plugins.cloudbees.tasks.app.CloudBeesAppDeployWar

class CloudBeesPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.plugins.apply(WarPlugin)
        addTasks(project)
    }

    private void addTasks(Project project) {
        project.tasks.withType(CloudBeesTask) {
            apiUrl = 'https://api.cloudbees.com/api'
            apiKey = project.property('cloudbeesApiKey')
            secret = project.property('cloudbeesApiSecret')
        }

        addAppTasks(project)
    }

    private void addAppTasks(Project project) {
        project.task('cloudBeesAppInfo', type: CloudBeesAppInfo) {
            appId = getAppId(project)
        }

        project.task('cloudBeesAppDeployWar', type: CloudBeesAppDeployWar) {
            appId = getAppId(project)
            message = project.hasProperty('message') ? project.message : null
            warFile = getWarFile(project)
        }
    }

    private String getAppId(Project project) {
        project.hasProperty('appId') ? project.appId : null
    }

    private File getWarFile(Project project) {
        project.hasProperty('warFile') ? new File(project.getProperty('warFile')) : project.tasks.getByName(WarPlugin.WAR_TASK_NAME).archivePath
    }
}