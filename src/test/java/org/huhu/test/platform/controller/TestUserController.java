package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.request.AddTestPlatformUserRequest;
import org.huhu.test.platform.model.response.QueryTestPlatformUserResponse;
import org.huhu.test.platform.model.response.QueryTestPlatformUsersResponse;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.huhu.test.platform.constant.TestPlatformRole.DEV;
import static org.huhu.test.platform.constant.TestPlatformRole.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@WithMockUser
@WebFluxTest(TestPlatformUserController.class)
class TestUserController {

    @Autowired
    WebTestClient webClient;

    @MockBean
    TestPlatformUserService userService;

    @Test
    void testQueryTestPlatformUsers() {
        QueryTestPlatformUsersResponse jack = new QueryTestPlatformUsersResponse();
        jack.setName("Jack");
        jack.setRoles(List.of("DEV", "USER"));

        QueryTestPlatformUsersResponse tom = new QueryTestPlatformUsersResponse();
        tom.setName("Tom");
        tom.setRoles(List.of("ADMIN", "TEST"));

        doReturn(Flux.just(jack, tom))
                .when(userService)
                .queryTestPlatformUsers();

        webClient.get()
                 .uri("/management/user")
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBodyList(QueryTestPlatformUsersResponse.class)
                 .hasSize(2);
    }

    @Test
    void testQueryTestPlatformUser() {
        QueryTestPlatformUserResponse jack = new QueryTestPlatformUserResponse();
        jack.setUsername("Jack");
        jack.setUserRoles(List.of("DEV", "ADMIN"));
        jack.setEnabled(true);

        doReturn(Mono.just(jack))
                .when(userService)
                .queryTestPlatformUser(anyString());

        webClient.get()
                 .uri("/management/user/{username}", "Jack")
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody()
                 .jsonPath("$.username").exists()
                 .jsonPath("$.username").isEqualTo("Jack")
                 .jsonPath("$.enabled").exists()
                 .jsonPath("$.enabled").isBoolean()
                 .jsonPath("$.enabled").isEqualTo(true)
                 .jsonPath("$.userRoles").exists()
                 .jsonPath("$.userRoles").isArray()
                 .jsonPath("$.userRoles[0]").isEqualTo("DEV")
                 .jsonPath("$.userRoles[1]").isEqualTo("ADMIN");
    }

    @Test
    void testAddTestPlatformUser() {
        doReturn(Mono.empty())
                .when(userService)
                .createTestPlatformUser(any(AddTestPlatformUserRequest.class));

        AddTestPlatformUserRequest request = new AddTestPlatformUserRequest();
        request.setUsername("Jack");
        request.setPassword("123456");
        request.setRoles(List.of(DEV, USER));

        webClient.mutateWith(SecurityMockServerConfigurers.csrf())
                 .put()
                 .uri("/management/user")
                 .bodyValue(request)
                 .exchange()
                 .expectStatus().isOk();
    }

    @Test
    void testAddTestPlatformUserWithEmptyUsername() {
        AddTestPlatformUserRequest request = new AddTestPlatformUserRequest();
        request.setPassword("123456");
        request.setRoles(List.of(DEV, USER));

        webClient.mutateWith(SecurityMockServerConfigurers.csrf())
                 .put()
                 .uri("/management/user")
                 .bodyValue(request)
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.code").exists()
                 .jsonPath("$.code").isNumber()
                 .jsonPath("$.code").isEqualTo(1001)
                 .jsonPath("$.message").exists()
                 .jsonPath("$.message").isEqualTo("request parameter invalid");
    }

    @Test
    void testAddTestPlatformUserWithEmptyPassword() {
        AddTestPlatformUserRequest request = new AddTestPlatformUserRequest();
        request.setUsername("Jack");
        request.setRoles(List.of(DEV, USER));

        webClient.mutateWith(SecurityMockServerConfigurers.csrf())
                 .put()
                 .uri("/management/user")
                 .bodyValue(request)
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.code").exists()
                 .jsonPath("$.code").isNumber()
                 .jsonPath("$.code").isEqualTo(1001)
                 .jsonPath("$.message").exists()
                 .jsonPath("$.message").isEqualTo("request parameter invalid");
    }

    @Test
    void testAddTestPlatformUserWithEmptyRoles() {
        AddTestPlatformUserRequest request = new AddTestPlatformUserRequest();
        request.setUsername("Jack");
        request.setPassword("123456");

        webClient.mutateWith(SecurityMockServerConfigurers.csrf())
                 .put()
                 .uri("/management/user")
                 .bodyValue(request)
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.code").exists()
                 .jsonPath("$.code").isNumber()
                 .jsonPath("$.code").isEqualTo(1001)
                 .jsonPath("$.message").exists()
                 .jsonPath("$.message").isEqualTo("request parameter invalid");
    }

    @Test
    void testAddTestPlatformUserWithEmptyBody() {
        webClient.mutateWith(SecurityMockServerConfigurers.csrf())
                 .put()
                 .uri("/management/user")
                 .bodyValue(new AddTestPlatformUserRequest())
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.code").exists()
                 .jsonPath("$.code").isNumber()
                 .jsonPath("$.code").isEqualTo(1001)
                 .jsonPath("$.message").exists()
                 .jsonPath("$.message").isEqualTo("request parameter invalid");
    }

}
