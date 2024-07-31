package com.redhat.jp.sample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/read-from/file")
public class CheckReadFromFileResource {

    @GET
    public Response readFromEnvironment() throws IOException {
        return Response.ok(Files.readAllBytes(Paths.get("/etc/os-release"))).build();
    }

}
