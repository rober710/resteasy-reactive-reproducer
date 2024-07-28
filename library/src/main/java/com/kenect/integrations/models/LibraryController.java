package com.kenect.integrations.models;

import io.quarkus.security.Authenticated;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * This controller is in a library. The purpose is to provide a set of endpoints that all services should have.
 */
@Authenticated
@NoArgsConstructor
@AllArgsConstructor
public abstract class LibraryController<T> {

    private LibraryService<T> testService;

    @POST
    @Path("/random-endpoint")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doSomething(RequestData requestData) {
        return Response.ok(testService.doSomething(requestData)).build();
    }
}
