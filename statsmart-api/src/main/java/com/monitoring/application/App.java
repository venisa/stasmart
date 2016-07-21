package com.monitoring.application;

import com.monitoring.config.SystemConfig;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * The main class that starts the Jetty Server
 *
 */
public class App
{
    private static final String CONFIG_PACKAGE = "com.monitoring";
    private static final int SERVER_PORT = SystemConfig.getIntProperty("server_port");

    public static void main( String[] args ) throws Exception
    {
        ResourceConfig config = new ApplicationResourceConfig();
        config.packages(CONFIG_PACKAGE);
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server server = new Server(SERVER_PORT);
        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.addServlet(servlet, "/*");

        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }

    }
}


