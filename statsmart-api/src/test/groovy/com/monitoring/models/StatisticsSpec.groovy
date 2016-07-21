package com.monitoring.models

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module

import spock.lang.Specification

/**
 * Test class for Statistics class
 */
class StatisticsSpec extends Specification {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new Jdk8Module().configureAbsentsAsNulls(false))



    def "Statistics serializes correctly"() {
        setup:
        CPU cpu = new CPU(new CPUFields("all", 5.06, 0, 0.23, 0.395), "host1")
        Memory memory = new Memory(new MemoryFields(125.4, 13.6, 13.33), "host1")
        Statistics statistics = new Statistics([cpu, memory])

        String expectedJson = ( """
                                 {
                                  "statistics": [
                                    {
                                      "fields": {
                                        "cpu": "all",
                                        "per_usr": 5.06,
                                        "per_nice": 0.0,
                                        "per_sys": 0.23,
                                        "per_io_wait": 0.395
                                      },
                                      "host": "host1",
                                      "metric": "cpu"
                                    },
                                    {
                                      "fields": {
                                        "total": 125.4,
                                        "used": 13.6,
                                        "free": 13.33
                                      },
                                      "host": "host1",
                                      "metric": "memory"
                                    }
                                  ]
                                }
                                """
                              ).replaceAll(/\s/, "")

        expect:
        MAPPER.writeValueAsString(statistics) == expectedJson
    }

}
