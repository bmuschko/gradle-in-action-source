# Source code for ["Gradle in Action"](http://www.manning.com/muschko/) [![Build Status](https://travis-ci.org/bmuschko/gradle-in-action-source.svg?branch=master)](https://travis-ci.org/bmuschko/gradle-in-action-source)

<kbd>
    <a href="http://www.manning.com/muschko/"><img src="https://images.manning.com/100/125/resize/book/1/6cc6a8b-de8f-40be-b234-916cd230389a/muschko.png" alt="Book cover"></a>
</kbd>

## Building the code

All source code used in the book is based on Gradle 1.7. Please use the [checked-in Wrapper](https://github.com/bmuschko/gradle-in-action-source/blob/master/gradle/wrapper/gradle-wrapper.properties#L6) to execute the samples. Please be aware that a later version of Gradle might not be compatible with the source code. 

The source code is organized into individual chapters. Chapter 1 contains Ant and Maven build scripts. Please refer to the build tool's online manual for instructions to install the runtime and execute the build script. You can find more information here:

* [Apache Ant](http://ant.apache.org/)
* [Apache Maven](http://maven.apache.org/)

Starting with chapter 2, all source code can be run using the Gradle Wrapper which means no runtime will have to be installed. For example to run the "Hello World" build script from chapter 2 open a shell/command prompt and navigate to the directory. You can execute the build script by referencing the Gradle Wrapper command line script and providing the task you would like to run.

### Windows:

    > cd gradle-in-action-source/chapter02/helloworld-task-doLast
    > ../../gradlew.bat helloWorld

### *nix:

    $ cd gradle-in-action-source/chapter02/helloworld-task-doLast 
    $ ../../gradlew helloWorld

## Putting it all together

Gradle in Action demonstrates how to implement build logic needed for various stages of a Continuous Delivery pipeline. While the book discusses specific functionality step by step, you might want to see it all put together. You can find the sample To Do application extended by typical project automation functionality (like testing strategies, publishing and deployment logic etc.) in a [dedicated repository](https://github.com/bmuschko/todo). Furthermore, you can find an updated version of the To Do application implemented with Spring Boot, built on a declarative Jenkins pipeline and deployed to Heroku in the blog post ["Build pipelines with Jenkins 2 by example"](http://bmuschko.com/blog/jenkins-build-pipeline/).

## Going further

The book discusses how to integrate third-party functionality by writing custom tasks or plugins. Some of the examples have been developed further and are available as full-fledged binary plugins ready to be used in your own projects.

* [Gradle CloudBees plugin](https://github.com/bmuschko/gradle-cloudbees-plugin): A plugin that provides support for managing applications and databases on CloudBees RUN@cloud.
* [Gradle Vagrant plugin](https://github.com/bmuschko/gradle-vagrant-plugin): A plugin for managing Vagrant boxes.

Unfortunately, I could not cover all topics readers were interested in. Here's a list of example projects that should get you started:

* [Gradle Android examples](https://github.com/bmuschko/gradle-android-examples): Gradle became the default build tool for Android projects. This repository demonstrates the most common uses cases for the [Gradle Android plugin](http://tools.android.com/tech-docs/new-build-system/user-guide).

## Continuous Integration

Most of the source code listings are automatically executed with the help of the [Gradle tooling API](http://www.gradle.org/docs/current/userguide/embedding.html). Whenever there's a change to the code, a corresponding [job](https://travis-ci.org/bmuschko/gradle-in-action-source) is triggered. Each build works through a list of predefined tasks for each chapter and verifies the expected outcome.