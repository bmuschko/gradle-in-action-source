package com.manning.gia

class ProjectVersion {
    final Integer major
    final Integer minor
    final String build

    ProjectVersion(Integer major, Integer minor, String build) {
        this.major = major
        this.minor = minor
        this.build = build
    }

    @Override
    String toString() {
        String fullVersion = "$major.$minor"

        if (build) {
            fullVersion += ".$build"
        }

        fullVersion
    }
}
