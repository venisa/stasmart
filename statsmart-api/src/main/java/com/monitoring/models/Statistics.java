package com.monitoring.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Statistics represents the JSON object returned to the user.
 */
public class Statistics {

    private final List<Metric> metrics;

    public Statistics(final List<Metric> metrics) {
        this.metrics = metrics;
    }

    @JsonProperty(value = "statistics")
    public List<Metric> getMetrics() {
        return metrics;
    }

}
