package com.manning.gia.sanitycheck.input

class Expectations {
    Result result = Result.SUCCESS

    static enum Result {
        SUCCESS('success'), FAILURE('failure')

        private static final Map RESULTS

        static {
            RESULTS = [:]

            values().each { result ->
                RESULTS.put(result.name, result)
            }
        }

        private final name

        private Result(name) {
            this.name = name
        }

        static Result getResultForName(String name) {
            if (!RESULTS.containsKey(name)) {
                throw new IllegalArgumentException("Unsupported result expectation: $name")
            }

            RESULTS[name]
        }
    }
}