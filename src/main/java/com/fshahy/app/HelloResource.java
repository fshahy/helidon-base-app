package com.fshahy.app;

import java.net.URI;
import java.util.Collections;

import io.helidon.config.Config;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("hello")
@RequestScoped
public class HelloResource {
    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());
    private final Config config;
    private final String applicationName;
    private final URI applicationUri;
    private BeanManager beanManager;

    @Inject
    public HelloResource(Config config,
                              @ConfigProperty(name = "app.name") String appName,
                              @ConfigProperty(name = "app.uri") URI appUri,
                              BeanManager beanManager) {
        this.config = config;
        this.applicationName = appName;
        this.applicationUri = appUri;
        this.beanManager = beanManager;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String message() {
        return "Hello from application " + applicationName;
    }

    @Path("/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getHello(@PathParam("name") String name) {
        return JSON.createObjectBuilder()
                .add("name", name)
                .add("appName", applicationName)
                .add("appUri", String.valueOf(applicationUri))
                .add("config", config.get("my.property").asString().get())
                .add("beanManager", beanManager.toString())
                .build();
    }
}
