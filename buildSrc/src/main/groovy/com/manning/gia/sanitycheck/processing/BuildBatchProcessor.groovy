package com.manning.gia.sanitycheck.processing

interface BuildBatchProcessor {
    void execute(File rootDir, String gradleVersion)
}