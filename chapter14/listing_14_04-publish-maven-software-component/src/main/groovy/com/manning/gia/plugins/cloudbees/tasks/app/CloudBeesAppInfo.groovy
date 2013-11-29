package com.manning.gia.plugins.cloudbees.tasks.app

import com.cloudbees.api.ApplicationInfo
import com.cloudbees.api.BeesClient
import org.gradle.api.tasks.Input
import com.manning.gia.plugins.cloudbees.tasks.CloudBeesTask

class CloudBeesAppInfo extends CloudBeesTask {
    @Input String appId

    CloudBeesAppInfo() {
        super('Returns the basic information about an application.')
    }

    @Override
    void executeAction(BeesClient client) {
        ApplicationInfo info = client.applicationInfo(getAppId())
        logger.quiet "Application title : $info.title"
        logger.quiet "          created : $info.created"
        logger.quiet "             urls : $info.urls"
        logger.quiet "           status : $info.status"
    }
}