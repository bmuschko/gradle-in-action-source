package com.manning.gia.sanitycheck.processing

import groovy.util.logging.Slf4j

import com.manning.gia.sanitycheck.input.Expectations
import com.manning.gia.sanitycheck.output.BuildResult

@Slf4j
class BuildVerifier {
    static final String SUCCESS_MESSAGE = 'BUILD SUCCESSFUL'
    static final String DEPRECATION_MESSAGE = 'Deprecated dynamic property'
    BuildRunner buildRunner = new BuildRunner()

    void verifySuccessfulExecution(File projectDir, String gradleVersion, String[] tasks, String[] args, Expectations expectations) {
        validateProjectDirectory(projectDir)
        BuildResult buildResult

        try {
            buildResult = buildRunner.executeBuildScript(projectDir, gradleVersion, tasks, args)
            boolean success = isSuccessMessage(buildResult)

            if (expectations.result == Expectations.Result.SUCCESS && !success) {
                throw new BuildVerificationException("Build was expected to be successful for script '$projectDir.canonicalPath' with tasks '${tasks.join(', ')}'.")
            }

            if (buildResult.output.contains(DEPRECATION_MESSAGE)) {
                throw new BuildVerificationException("Build script '$projectDir.canonicalPath' with tasks '${tasks.join(', ')}' contains deprecated property.")
            }
        }
        catch (Exception e) {
            log.error "Build for script '$projectDir.canonicalPath' failed to execute."

            if (expectations.result != Expectations.Result.FAILURE) {
                throw new BuildVerificationException("Build was expected to be failing for script '$projectDir.canonicalPath' with tasks '${tasks.join(', ')}'.")
            }
        }
    }

    private void validateProjectDirectory(File projectDir) {
        if (!projectDir.exists()) {
            throw new FileNotFoundException("The project to be tested does not exist: $projectDir")
        }
    }

    private boolean isSuccessMessage(BuildResult buildResult) {
        buildResult.output.contains(SUCCESS_MESSAGE)
    }
}