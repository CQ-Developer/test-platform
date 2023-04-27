package org.huhu.test.platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@WithMockUser(roles = "DEV")
class TestApplication {

    @Autowired
    WebTestClient webTestClient;

    /**
     * todo 暂时不执行该测试
     */
    void test() {
        webTestClient.get()
                     .uri("/actuator/health")
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody()
                     .jsonPath("$.status")
                     .isEqualTo("UP");
    }

}
