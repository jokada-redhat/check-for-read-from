package com.redhat.jp.sample;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/read-from/env")
public class CheckReadFromEnvResource {

    @ConfigProperty(name = "dummy.env")
    String value1;

    @GET
    public Response readFromEnvironment() {
        Log.infov("Read env: {0}={1}", "DUMMY_ENV", value1);
        return Response.ok(value1).build();
    }

}