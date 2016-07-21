package com.monitoring.config

import com.monitoring.config.SystemConfig

import spock.lang.Specification

/**
 * Tests for SystemConfig class
 */
class SystemConfigSpec extends Specification {
    private static final String STRING_PROPERTY_KEY = "jdbc_driver"
    private static final String EXPECTED_STRING_PROPERTY_VALUE = "com.mysql.jdbc.Driver"

    private static final String INT_PROPERTY_KEY = "server_port"

    private static final int EXPECTED_INT_PROPERTY_VALUE = 2222;

    def "We can read the String config properties correctly from the config file"() {
        expect:
        SystemConfig.getStringProperty(STRING_PROPERTY_KEY) == EXPECTED_STRING_PROPERTY_VALUE
    }

    def "We can read the int config properties correctly from the config file"() {
        expect:
        SystemConfig.getIntProperty(INT_PROPERTY_KEY) == EXPECTED_INT_PROPERTY_VALUE
    }
}
