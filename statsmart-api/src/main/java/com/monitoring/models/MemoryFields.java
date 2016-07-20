package com.monitoring.models;

/**
 * POJO for all the information collected about memory of a host
 */
public class MemoryFields {

    private final double total;
    private final double used;
    private final double free;

    public MemoryFields(double total, double used, double free) {
        this.total = total;
        this.used = used;
        this.free = free;
    }

    public double getTotal() {
        return total;
    }

    public double getUsed() {
        return used;
    }

    public double getFree() {
        return free;
    }
}
