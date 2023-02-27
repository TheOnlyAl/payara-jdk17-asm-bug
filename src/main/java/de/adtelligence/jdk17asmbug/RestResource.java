package de.adtelligence.jdk17asmbug;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("resource")
public class RestResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getSecrets() {
        return Response.ok(new MyRecord(1, "Name").toString())
            .build();
    }
}