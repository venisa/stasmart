package com.monitoring.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Abstract class for a metric. Any new metric should obey the contract specified by Metric
 */
public abstract class Metric {
    private final String name;
    private final Object fields;
    private final String host;

    public Metric(String name, Object fields, String host) {
        this.name = name;
        this.fields = fields;
        this.host = host;
    }

    @JsonProperty(value = "metric")
    public String getName() {
        return name;
    }

    public Object getFields() {
        return fields;
    }

    public String getHost() {
        return host;
    }

}
