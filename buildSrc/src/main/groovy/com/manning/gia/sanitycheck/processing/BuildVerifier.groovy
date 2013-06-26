package com.manning.gia.sanitycheck.processing

import com.manning.gia.sanitycheck.output.BuildResult

class BuildVerifier {
    static final String SUCCESS_MESSAGE = 'BUILD SUCCESSFUL'
    static final String DEPRECATION_MESSAGE = 'Deprecated dynamic property'
    BuildRunner buildRunner = new BuildRunner()

    void verifySuccessfulExecution(File projectDir, String gradleVersion, String... tasks) {
        validateProjectDirectory(projectDir)
        BuildResult buildResult = buildRunner.executeBuildScript(projectDir, gradleVersion, tasks)

        if(!buildResult.output.contains(SUCCESS_MESSAGE)) {
            throw new BuildVerificationException("Build was not successful for script '$projectDir.canonicalPath' with tasks '${tasks.join(', ')}'.")
        }
        if(buildResult.output.contains(DEPRECATION_MESSAGE)) {
            throw new BuildVerificationException("Build script '$projectDir.canonicalPath' with tasks '${tasks.join(', ')}' contains deprecated property.")
        }
    }

    private void validateProjectDirectory(File projectDir) {
        if(!projectDir.exists()) {
            throw new FileNotFoundException("The project to be tested does not exist: $projectDir")
        }
    }
}