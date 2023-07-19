package com.intexsoft.salary_distribution;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class WireMockBaseTestIT {
    @Value("${wiremock.server.port}")
    protected int serverPort;

    protected WireMockServer wireMockServer;

    @BeforeAll
    void setupServer() {
        wireMockServer = new WireMockServer(serverPort);
        wireMockServer.start();
    }

    @AfterAll
    void shutdownServer() {
        wireMockServer.shutdownServer();
    }
}
