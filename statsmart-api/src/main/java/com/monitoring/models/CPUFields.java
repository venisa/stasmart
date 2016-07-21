package com.monitoring.models;

/**
 * POJO for all the information collected about CPU performance
 */
public class CPUFields {

    private final String cpu;
    private final double per_usr;
    private final double per_nice;
    private final double per_sys;
    private final double per_io_wait;

    public CPUFields(
            String cpu,
            double per_usr,
            double per_nice,
            double per_sys,
            double per_io_wait
    ) {
        this.cpu = cpu;
        this.per_usr = per_usr;
        this.per_nice = per_nice;
        this.per_sys = per_sys;
        this.per_io_wait = per_io_wait;
    }

    public String getCpu() {
        return cpu;
    }

    public double getPer_usr() {
        return per_usr;
    }

    public double getPer_nice() {
        return per_nice;
    }

    public double getPer_sys() {
        return per_sys;
    }

    public double getPer_io_wait() {
        return per_io_wait;
    }
}
