package com.monitoring.models;

/**
 * POJO for CPU metric
 */
public class CPU extends Metric {

    private final static String NAME = "cpu";

    public CPU(CPUFields fields, String host) {
        super(NAME, fields, host);
    }
}
