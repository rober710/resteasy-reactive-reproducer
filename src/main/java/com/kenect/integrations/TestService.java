package com.kenect.integrations;

import io.quarkus.rest.client.reactive.runtime.DefaultMicroprofileRestClientExceptionMapper;
import io.quarkus.rest.client.reactive.runtime.MicroProfileRestClientResponseFilter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.RuntimeType;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Configuration;
import java.util.Collections;
import java.util.List;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.jboss.resteasy.reactive.client.api.QuarkusRestClientProperties;
import org.jboss.resteasy.reactive.client.impl.ClientBuilderImpl;
import org.jboss.resteasy.reactive.common.jaxrs.ConfigurationImpl;

@ApplicationScoped
public class TestService {

    public String getResponse() {
        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        ((ClientBuilderImpl) clientBuilder).followRedirects(true);

        List<ResponseExceptionMapper<?>> exceptionMappers = Collections.singletonList(new DefaultMicroprofileRestClientExceptionMapper());
        Client httpClient = clientBuilder.withConfig(buildHttpConfig())
                .register(new MicroProfileRestClientResponseFilter(exceptionMappers)).build();
        /*try (Response response = httpClient.target("http://httpbin.org/status/418").request().get()) {
            System.out.println(response.readEntity(String.class));
        }*/
        try {
            String payload = httpClient.target("http://httpbin.org/status/418").request().get(String.class);
            System.out.println(payload);
            return payload;
        } catch (Exception e) {
            if (e instanceof WebApplicationException webApplicationException) {
                String payload = webApplicationException.getResponse().readEntity(String.class);
                System.out.println(e.getMessage() + " entity " + payload);
                return payload;
            }
            e.printStackTrace();
            throw e;
        }
    }

    private Configuration buildHttpConfig() {
        ConfigurationImpl configuration = new ConfigurationImpl(RuntimeType.CLIENT);
        configuration.property(QuarkusRestClientProperties.CONNECTION_TTL, 7);
        configuration.property(QuarkusRestClientProperties.CONNECTION_POOL_SIZE, 21);
        configuration.property(QuarkusRestClientProperties.KEEP_ALIVE_ENABLED, true);
        configuration.property(QuarkusRestClientProperties.MAX_REDIRECTS, 10);
        return configuration;
    }
}
