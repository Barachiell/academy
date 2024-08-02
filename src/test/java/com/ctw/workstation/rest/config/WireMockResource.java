package com.ctw.workstation.rest.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;


import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

public class WireMockResource implements QuarkusTestResourceLifecycleManager {

    WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        if(wireMockServer == null) {
            wireMockServer = new WireMockServer();
            wireMockServer.start();
        }


        return Map.of("external-api.url", wireMockServer.baseUrl());
    }

    @Override
    public void stop() {
        wireMockServer.stop();
    }
}
