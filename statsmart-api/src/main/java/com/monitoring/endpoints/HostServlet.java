package com.monitoring.endpoints;

import com.monitoring.helpers.Datasource;
import com.monitoring.helpers.ResponseUtils;
import com.monitoring.helpers.SQLHelper;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Host Servlet responds to the host endpoint which allows user to query information about all the hosts in the system
 * and to get performance metrics of a particular host
 */
@Path("/hosts")
public class HostServlet {

    private final com.monitoring.helpers.SQLHelper SQLHelper;

    @Inject
    public HostServlet(Datasource dataSource) {
        SQLHelper = new SQLHelper(dataSource.getDatabaseConnection());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * This method corresponds to the '/hosts' endpoint. It returns a Json containing ip address and host name of all
     * hosts in the system.
     */
    public Response getHosts() {

        return ResponseUtils.getResponse(SQLHelper.getHosts());
    }

    @GET
    @Path("{hostId}")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * This method corresponds to the '/hosts/{hostId}' endpoint. It gives a summary of the performance of all the hosts in the
     * system.
     */
    public Response getAverage(@PathParam("hostId") String hostId) {
        return ResponseUtils.getResponse(SQLHelper.getAverage(hostId));
    }

    @GET
    @Path("{hostId}/latest")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * This method corresponds to the '/hosts/{hostId}/latest endpoint. It gives the latest performance metrics of a particular host.
     */
    public Response getLatest(@PathParam("hostId") String hostId) {
        return ResponseUtils.getResponse(SQLHelper.getLatest(hostId));
    }

    //TODO implement point in time statistics

}
