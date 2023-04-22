package org.huhu.test.platform.controller;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsIterableContaining;
import org.huhu.test.platform.model.response.ErrorResponse;
import org.huhu.test.platform.model.response.UserDetailQueryResponse;
import org.huhu.test.platform.model.response.UserQueryResponse;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.huhu.test.platform.constant.TestPlatformRoleLevel.ADMIN;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.DEV;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.USER;
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
    void queryUser() {
        var u1 = new UserQueryResponse("u1", List.of(USER, DEV));
        var u2 = new UserQueryResponse("u2", List.of(ADMIN));
        var response = Flux.just(u1, u2);
        doReturn(response)
                .when(userService)
                .queryTestPlatformUser();
        webClient.get()
                 .uri("/user")
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBodyList(UserQueryResponse.class)
                 .hasSize(2)
                 .contains(u1, u2);
    }

    @Test
    void testQueryUser() {
        var now = LocalDateTime.of(2000, 1, 1, 1, 1);
        var jack = new UserDetailQueryResponse("jack", List.of(USER), true, false, now, now.plusDays(1L));
        var response = Mono.just(jack);
        doReturn(response)
                .when(userService)
                .queryTestPlatformUser(anyString());
        webClient.get()
                 .uri("/user/{username}", "jack")
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody(UserDetailQueryResponse.class)
                 .value(UserDetailQueryResponse::username, IsEqual.equalTo("jack"))
                 .value(UserDetailQueryResponse::roleLevels, IsIterableContaining.hasItem(USER))
                 .value(UserDetailQueryResponse::enabled, IsEqual.equalTo(true))
                 .value(UserDetailQueryResponse::locked, IsEqual.equalTo(false))
                 .value(UserDetailQueryResponse::registerTime, IsEqual.equalTo(LocalDateTime.of(2000, 1, 1, 1, 1)))
                 .value(UserDetailQueryResponse::expiredTime, IsEqual.equalTo(LocalDateTime.of(2000, 1, 2, 1, 1)));
    }

    @Test
    void testQueryUserError() {
        webClient.get()
                 .uri("/user/{username}", "u1")
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody(ErrorResponse.class)
                 .value(ErrorResponse::code, IsEqual.equalTo(1000))
                 .value(ErrorResponse::message, IsEqual.equalTo("client error"));
    }

    @Test
    void createUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void renewUser() {
    }

    @Test
    void enableUser() {
    }

    @Test
    void disableUser() {
    }

    @Test
    void lockUser() {
    }

    @Test
    void unlockUser() {
    }

}