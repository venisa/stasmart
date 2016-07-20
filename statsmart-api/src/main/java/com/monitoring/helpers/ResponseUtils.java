package com.monitoring.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.core.Response;

/**
 * A utility class to generate Json response
 */
public class ResponseUtils {

    public static Response getResponse(Object result) {
        ObjectMapper mapper = new ObjectMapper();

        String response;
        try {
            response =  mapper.writeValueAsString(result);
        } catch (Exception e) {
            //TODO implement logging
            throw new IllegalStateException("No doughnut for you");
        }

        return Response.ok().entity(response).build();
    }
}
