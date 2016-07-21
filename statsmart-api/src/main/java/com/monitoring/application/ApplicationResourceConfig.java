package com.monitoring.application;

import com.monitoring.helpers.Datasource;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Singleton;

/**
 * The Resource Configuration for StatsSmart webservice
 */
public class ApplicationResourceConfig extends ResourceConfig {

    public ApplicationResourceConfig() {
        register(new AbstractBinder() {
                 @Override
                 protected void configure() {
                     bindAsContract(Datasource.class).in(Singleton.class);
                 }
        });
    }
}
