package org.huhu.test.platform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

@WithMockUser(roles = {"DEV", "ADMIN"})
@SpringBootTest
@AutoConfigureWebTestClient
class TestApplication {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void test() {
        webTestClient
                .get()
                .uri("/actuator/health")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").exists()
                .jsonPath("$.status").isEqualTo("UP");
    }

}
