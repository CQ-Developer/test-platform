package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.request.AddTestPlatformUserRequest;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.huhu.test.platform.constant.TestPlatformRole.DEV;
import static org.huhu.test.platform.constant.TestPlatformRole.USER;

@WebFluxTest(TestPlatformUserController.class)
class TestUserController {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    TestPlatformUserService testPlatformUserService;

    @Test
    @WithMockUser(roles = "DEV")
    void testAddTestPlatformUser() {
        AddTestPlatformUserRequest request = new AddTestPlatformUserRequest();
        request.setUsername("user");
        request.setPassword("12345678");
        request.setRoles(List.of(USER, DEV));
        webTestClient
                .post()
                .uri("/rest/user/add")
                .bodyValue(request)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.username").isEqualTo("user");
    }

}
