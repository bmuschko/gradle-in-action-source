# Source code for ["Gradle in Action"](http://www.manning.com/muschko/)

The source code is organized into individual chapters. Chapter 1 contains Ant and Maven build scripts. Please refer to the build tool's online manual for instructions to install the runtime and execute the build script. You can find more information here:

* [Apache Ant](http://ant.apache.org/)
* [Apache Maven](http://maven.apache.org/)

Starting with chapter 2, all source code can be run using the Gradle Wrapper which means no runtime will have to be installed. For example to run the "Hello World" build script from chapter 2 open a shell/command prompt and navigate to the directory. You can execute the build script by referencing the Gradle Wrapper command line script and providing the task you would like to run.

## Windows:

    > cd gradle-in-action-source/chapter2/helloworld-task-doLast
    > ../../gradlew.bat helloWorld

## *nix:

    $ cd gradle-in-action-source/chapter2/helloworld-task-doLast 
    $ ../../gradlew helloWorld