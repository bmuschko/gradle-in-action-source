package com.manning.gia.sanitycheck.input

import groovy.json.JsonSlurper

class JsonTestSetReader implements TestSetReader {
    @Override
    def parse(Reader reader) {
        new JsonSlurper().parse(reader)
    }
}