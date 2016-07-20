package com.monitoring.models;

/**
 * POJO for Memory metric
 */
public class Memory extends Metric {

    private final static String NAME = "memory";

    public Memory(MemoryFields fields, String host) {
        super(NAME, fields, host);
    }

}
