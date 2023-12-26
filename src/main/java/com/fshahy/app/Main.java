package com.fshahy.app;

import io.helidon.microprofile.server.Server;

public class Main {

    private Main() {
    }

    public static void main(String[] args) {
        Server server = Server.builder()
                .host("localhost")
                .build();

        server.start();

        String endpoint = "http://" + server.host() + ":" + server.port();
        System.out.println("Started application on     " + endpoint + "/hello");
        System.out.println("Metrics available on       " + endpoint + "/metrics");
        System.out.println("Heatlh checks available on " + endpoint + "/health");

    }
}
