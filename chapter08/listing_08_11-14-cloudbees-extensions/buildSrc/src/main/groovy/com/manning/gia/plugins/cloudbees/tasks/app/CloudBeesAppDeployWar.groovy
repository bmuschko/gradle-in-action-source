package com.manning.gia.plugins.cloudbees.tasks.app

import com.cloudbees.api.ApplicationDeployArchiveResponse
import com.cloudbees.api.BeesClient
import com.manning.gia.plugins.cloudbees.tasks.CloudBeesTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional

class CloudBeesAppDeployWar extends CloudBeesTask {
    @Input String appId
    @Input
    @Optional String message
    @InputFile File warFile

    CloudBeesAppDeployWar() {
        super('Deploys a new version of an application using a WAR file.')
    }

    @Override
    void executeAction(BeesClient client) {
        logger.quiet "Deploying WAR '${getWarFile()}' to application ID '${getAppId()}' with message '${getMessage()}'"
        ApplicationDeployArchiveResponse response = client.applicationDeployWar(getAppId(), null, getMessage(), getWarFile(), null, null)
        logger.quiet "Application uploaded successfully to: $response.url"
    }
}
