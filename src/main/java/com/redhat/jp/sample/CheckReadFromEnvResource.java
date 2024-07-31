package com.redhat.jp.sample;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/read-from/env")
public class CheckReadFromEnvResource {

    @ConfigProperty(name = "dummy.env_1")
    String value1;

    @GET
    public Response readFromEnvironment() {
        return Response.ok(value1).build();
    }

}