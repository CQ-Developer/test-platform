package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.request.UserCreationRequest;
import org.huhu.test.platform.model.response.UserDetailQueryResponse;
import org.huhu.test.platform.model.response.UserQueryResponse;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@WithMockUser
@WebFluxTest(TestPlatformUserController.class)
class TestPlatformUserControllerTest {

    @Autowired
    WebTestClient webClient;

    @MockBean
    TestPlatformUserService userService;

    @Test
    void queryTestPlatformUsers() {
        var jack = new UserQueryResponse("Jack", List.of("DEV", "USER"));
        var tom = new UserQueryResponse("Tom", List.of("ADMIN", "TEST"));

        doReturn(Flux.just(jack, tom))
                .when(userService)
                .queryTestPlatformUsers();

        webClient.get()
                 .uri("/management/user")
                 .exchange()
                 .expectStatus().isOk()
                 .expectBodyList(UserQueryResponse.class)
                 .hasSize(2);
    }

    @Test
    void queryTestPlatformUsersError() {
        doReturn(Flux.error(new RuntimeException("test")))
                .when(userService)
                .queryTestPlatformUsers();

        webClient.get()
                 .uri("/management/user")
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.code").exists()
                 .jsonPath("$.code").isNumber()
                 .jsonPath("$.code").isEqualTo(3000);
    }

    @Test
    void queryTestPlatformUser() {
        var jack = new UserDetailQueryResponse("Jack", List.of("DEV", "ADMIN"), true, null, null, null);

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
    void queryTestPlatformUserError() {
        doReturn(Mono.error(new RuntimeException("test")))
                .when(userService)
                .queryTestPlatformUser(anyString());

        webClient.get()
                 .uri("/management/user/{username}", "Jack")
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.code").exists()
                 .jsonPath("$.code").isNumber()
                 .jsonPath("$.code").isEqualTo(3000);
    }

    @Test
    void queryTestPlatformUserWithIllegalUsername() {
        webClient.get()
                 .uri("/management/user/{username}", "Tom")
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.code").exists()
                 .jsonPath("$.code").isNumber()
                 .jsonPath("$.code").isEqualTo(1001);
    }

    @Test
    void createTestPlatformUser() {
        doReturn(Mono.empty())
                .when(userService)
                .createTestPlatformUser(any(UserCreationRequest.class));

        var request = new UserCreationRequest();
        request.setUsername("Jack");
        request.setPassword("123456");
        request.setRoles(List.of("DEV", "USER"));

        webClient.mutateWith(SecurityMockServerConfigurers.csrf())
                 .put()
                 .uri("/management/user")
                 .bodyValue(request)
                 .exchange()
                 .expectStatus().isOk();
    }

    @Test
    void createTestPlatformUserError() {
        doReturn(Mono.error(new RuntimeException("test")))
                .when(userService)
                .createTestPlatformUser(any(UserCreationRequest.class));

        var request = new UserCreationRequest();
        request.setUsername("Jack");
        request.setPassword("123456");
        request.setRoles(List.of("DEV", "USER"));

        webClient.mutateWith(SecurityMockServerConfigurers.csrf())
                 .put()
                 .uri("/management/user")
                 .bodyValue(request)
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.code").exists()
                 .jsonPath("$.code").isNumber()
                 .jsonPath("$.code").isEqualTo(3000);
    }

    @Test
    void createTestPlatformUserWithEmptyUsername() {
        var request = new UserCreationRequest();
        request.setPassword("123456");
        request.setRoles(List.of("DEV", "USER"));

        webClient.mutateWith(SecurityMockServerConfigurers.csrf())
                 .put()
                 .uri("/management/user")
                 .bodyValue(request)
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.code").exists()
                 .jsonPath("$.code").isNumber()
                 .jsonPath("$.code").isEqualTo(1001);
    }

    @Test
    void createTestPlatformUserWithEmptyPassword() {
        var request = new UserCreationRequest();
        request.setUsername("Jack");
        request.setRoles(List.of("DEV", "USER"));

        webClient.mutateWith(SecurityMockServerConfigurers.csrf())
                 .put()
                 .uri("/management/user")
                 .bodyValue(request)
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.code").exists()
                 .jsonPath("$.code").isNumber()
                 .jsonPath("$.code").isEqualTo(1001);
    }

    @Test
    void createTestPlatformUserWithEmptyRoles() {
        var request = new UserCreationRequest();
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
                 .jsonPath("$.code").isEqualTo(1001);
    }

    @Test
    void deleteTestPlatformUser() {
        doReturn(Mono.empty())
                .when(userService)
                .deleteTestPlatformUser(anyString());

        webClient.mutateWith(SecurityMockServerConfigurers.csrf())
                 .delete()
                 .uri("/management/user/Jack")
                 .exchange()
                 .expectStatus().isOk();
    }

    @Test
    void deleteTestPlatformUserError() {
        doReturn(Mono.error(new RuntimeException("test")))
                .when(userService)
                .deleteTestPlatformUser(anyString());

        webClient.mutateWith(SecurityMockServerConfigurers.csrf())
                 .delete()
                 .uri("/management/user/Jack")
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.code").exists()
                 .jsonPath("$.code").isNumber()
                 .jsonPath("$.code").isEqualTo(3000);
    }

    @Test
    void deleteTestPlatformUserWithIllegalUsername() {
        webClient.mutateWith(SecurityMockServerConfigurers.csrf())
                 .delete()
                 .uri("/management/user/Tom")
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.code").exists()
                 .jsonPath("$.code").isNumber()
                 .jsonPath("$.code").isEqualTo(1001);
    }

}
