# Source code for ["Gradle in Action"](http://www.manning.com/muschko/)

[![Build Status](https://bmuschko.ci.cloudbees.com/buildStatus/icon?job=gradle-in-action-source)](https://bmuschko.ci.cloudbees.com/job/gradle-in-action-source/)

# Building the code

The source code is organized into individual chapters. Chapter 1 contains Ant and Maven build scripts. Please refer to the build tool's online manual for instructions to install the runtime and execute the build script. You can find more information here:

* [Apache Ant](http://ant.apache.org/)
* [Apache Maven](http://maven.apache.org/)

Starting with chapter 2, all source code can be run using the Gradle Wrapper which means no runtime will have to be installed. For example to run the "Hello World" build script from chapter 2 open a shell/command prompt and navigate to the directory. You can execute the build script by referencing the Gradle Wrapper command line script and providing the task you would like to run.

## Windows:

    > cd gradle-in-action-source/chapter02/helloworld-task-doLast
    > ../../gradlew.bat helloWorld

## *nix:

    $ cd gradle-in-action-source/chapter02/helloworld-task-doLast 
    $ ../../gradlew helloWorld
    
# Continuous Integration

Most of the source code listings are automatically executed with the help of the [Gradle tooling API](http://www.gradle.org/docs/current/userguide/embedding.html). Whenever there's a change to the code, a corresponding [Jenkins job](https://bmuschko.ci.cloudbees.com/job/gradle-in-action-source/) is triggered. Each build works through a list of predefined tasks for each chapter and verifies the expected outcome.