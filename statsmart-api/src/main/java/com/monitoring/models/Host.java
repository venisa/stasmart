package com.monitoring.models;

/**
 * POJO for host
 */
public class Host {

    private final String ip;
    private final String name;

    public Host(final String ip, final String name) {
        this.ip = ip;
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public String getName() {
        return name;
    }
}
