package com.monitoring.models

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module

import spock.lang.Specification

/**
 * Tests for Host class
 */
class HostSpec extends Specification {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new Jdk8Module().configureAbsentsAsNulls(false))

    Host host1
    Host host2

    def setup() {
        host1 = new Host("192.168.0.11", "host1")
        host2 = new Host("192.168.0.12", "host2")
    }

    def "Test single host serializes correctly" () {
        setup:
        String expectedJsonString = """{"ip":"192.168.0.11","name":"host1"}"""

        expect:
        MAPPER.writeValueAsString(host1) == expectedJsonString
    }

    def "Test multiple hosts serialize correctly" () {
        setup:
        String expectedJsonString = """[{"ip":"192.168.0.11","name":"host1"},{"ip":"192.168.0.12","name":"host2"}]"""

        expect:
        MAPPER.writeValueAsString([host1,host2]) == expectedJsonString
    }
}
