package com.kenect.integrations;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

@Path("/hello")
@RequiredArgsConstructor
public class GreetingResource {

    private final TestService testService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return testService.getResponse();
    }
}
