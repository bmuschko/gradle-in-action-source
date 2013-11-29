package com.manning.gia.plugins.cloudbees.tasks.app

import com.cloudbees.api.ApplicationInfo
import com.cloudbees.api.BeesClient
import org.gradle.api.*
import org.gradle.api.tasks.*

class CloudBeesAppInfo extends DefaultTask {
    @Input String apiUrl
    @Input String apiKey
    @Input String secret
    @Input String apiFormat
    @Input String apiVersion
    @Input String appId

    CloudBeesAppInfo() {
        description = 'Returns the basic information about an application.'
        group = 'CloudBees'
    }

    @TaskAction
    void start() {
        BeesClient client = new BeesClient(apiUrl, apiKey, secret, apiFormat, apiVersion)
        ApplicationInfo info

        try {
            info = client.applicationInfo(appId)
        }
        catch (Exception e) {
            throw new GradleException(e.message)
        }

        logger.quiet "Application id : $info.id"
        logger.quiet "         title : $info.title"
        logger.quiet "       created : $info.created"
        logger.quiet "          urls : $info.urls"
        logger.quiet "        status : $info.status"
    }
}