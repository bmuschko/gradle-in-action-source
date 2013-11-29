module.exports = function (grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        jshint: {
            all: ['src/main/webapp/js/app/*.js']
        }
    });

    grunt.loadNpmTasks('grunt-contrib-jshint');
};