package com.redhat.jp.sample;

import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/read-from/env-raw")
public class CheckReadFromEnvRawResource {

    @GET
    public Response readFromEnvironment() {
        String value = System.getProperty("DUMMY_ENV_RAW");
        Log.infov("Read env: {0}={1}", "DUMMY_ENV_RAW", value);
        return Response.ok(value).build();
    }

}