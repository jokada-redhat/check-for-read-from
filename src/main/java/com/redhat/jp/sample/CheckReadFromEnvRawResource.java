package com.redhat.jp.sample;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/read-from/env-raw")
public class CheckReadFromEnvRawResource {

    @GET
    public Response readFromEnvironment() {
        return Response.ok(System.getProperty("DUMMY_ENV_RAW")).build();
    }

}