package org.huhu.test.platform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class TestApp {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void test() {
        webTestClient.get()
                     .uri("/actuator/health")
                     .exchange()
                     .expectStatus().is2xxSuccessful()
                     .expectBody().jsonPath("$.status").isEqualTo("UP");
    }

}
