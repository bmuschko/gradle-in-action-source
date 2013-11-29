package com.manning.gia.plugins.cloudbees.tasks.app

import com.cloudbees.api.ApplicationDeployArchiveResponse
import com.cloudbees.api.BeesClient
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction

class CloudBeesAppDeployWar extends DefaultTask {
    @Input String apiUrl
    @Input String apiKey
    @Input String secret
    @Input String apiFormat
    @Input String apiVersion
    @Input String appId
    @Input String message
    @InputFile File warFile

    CloudBeesAppDeployWar() {
        description = 'Deploys a new version of an application using a WAR file.'
        group = 'CloudBees'
    }

    @TaskAction
    void start() {
        logger.quiet "Deploying WAR '$warFile' to application ID '$appId' with message '$message'"
        BeesClient client = new BeesClient(apiUrl, apiKey, secret, apiFormat, apiVersion)
        ApplicationDeployArchiveResponse response

        try {
            response = client.applicationDeployWar(appId, null, message, warFile, null, null)
        }
        catch (Exception e) {
            throw new GradleException(e.message)
        }

        logger.quiet "Application uploaded successfully to: '$response.url'"
    }
}
