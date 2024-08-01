package com.redhat.jp.sample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/read-from/file")
public class CheckReadFromFileResource {

    @ConfigProperty(name = "dummy.file")
    String filePath;

    @GET
    public Response readFromFile() throws IOException {
        Log.infov("Read file path: ", filePath);
        return Response.ok(Files.readAllBytes(Paths.get(filePath))).build();
    }

}
