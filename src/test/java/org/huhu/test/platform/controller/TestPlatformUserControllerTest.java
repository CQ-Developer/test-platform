package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.request.UserCreateRequest;
import org.huhu.test.platform.model.request.UserModifyRequest;
import org.huhu.test.platform.model.response.ErrorResponse;
import org.huhu.test.platform.model.response.UserDetailQueryResponse;
import org.huhu.test.platform.model.response.UserQueryResponse;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsIterableContaining.hasItem;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.ADMIN;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.DEV;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@WithMockUser
@WebFluxTest(TestPlatformUserController.class)
class TestPlatformUserControllerTest {

    @Autowired
    WebTestClient webClient;

    @MockBean
    TestPlatformUserService userService;

    @Test
    void querySelfUser() {
        var now = LocalDateTime.of(2000, 1, 1, 1, 1);
        var jack = new UserDetailQueryResponse("jack", List.of(USER), true, false, now, now.plusDays(1L));
        var response = Mono.just(jack);
        doReturn(response)
                .when(userService)
                .queryTestPlatformUserDetail(anyString());
        webClient.get()
                 .uri("/user")
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody(UserDetailQueryResponse.class)
                 .value(UserDetailQueryResponse::username, equalTo("jack"))
                 .value(UserDetailQueryResponse::roleLevels, hasItem(USER))
                 .value(UserDetailQueryResponse::enabled, equalTo(true))
                 .value(UserDetailQueryResponse::locked, equalTo(false))
                 .value(UserDetailQueryResponse::registerTime, equalTo(now))
                 .value(UserDetailQueryResponse::expiredTime, equalTo(now.plusDays(1L)));
    }

    @Test
    void queryUserDetail() {
        var now = LocalDateTime.of(2000, 1, 1, 1, 1);
        var jack = new UserDetailQueryResponse("jack", List.of(USER), true, false, now, now.plusDays(1L));
        var response = Mono.just(jack);
        doReturn(response)
                .when(userService)
                .queryTestPlatformUserDetail(anyString());
        webClient.get()
                 .uri("/user/{username}", "jack")
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody(UserDetailQueryResponse.class)
                 .value(UserDetailQueryResponse::username, equalTo("jack"))
                 .value(UserDetailQueryResponse::roleLevels, hasItem(USER))
                 .value(UserDetailQueryResponse::enabled, equalTo(true))
                 .value(UserDetailQueryResponse::locked, equalTo(false))
                 .value(UserDetailQueryResponse::registerTime, equalTo(now))
                 .value(UserDetailQueryResponse::expiredTime, equalTo(now.plusDays(1L)));
    }

    @Test
    void queryUserDetailError() {
        webClient.get()
                 .uri("/user/{username}", "u1")
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody(ErrorResponse.class)
                 .value(ErrorResponse::code, equalTo(1000))
                 .value(ErrorResponse::message, equalTo("client error"));
    }

    @Test
    void queryUsers() {
        var u1 = new UserQueryResponse("u1", List.of(USER, DEV));
        var u2 = new UserQueryResponse("u2", List.of(ADMIN));
        var response = Flux.just(u1, u2);
        doReturn(response)
                .when(userService)
                .queryTestPlatformUser();
        webClient.get()
                 .uri("/user/all")
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBodyList(UserQueryResponse.class)
                 .hasSize(2)
                 .contains(u1, u2);
    }

    @Test
    void createUser() {
        doReturn(Mono.empty())
                .when(userService)
                .createTestPlatformUser(any(UserCreateRequest.class));
        var request = new UserCreateRequest("tester", "123456", Set.of(USER), LocalDateTime.now());
        webClient.mutateWith(csrf())
                 .put()
                 .uri("/user")
                 .bodyValue(request)
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody()
                 .isEmpty();
    }

    @ParameterizedTest
    @CsvSource({"u1, 123456", "tester, 123"})
    void createUserError(String username, String password) {
        var request = new UserCreateRequest(username, password, null, null);
        webClient.mutateWith(csrf())
                 .put()
                 .uri("/user")
                 .bodyValue(request)
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody(ErrorResponse.class)
                 .value(ErrorResponse::code, equalTo(1000))
                 .value(ErrorResponse::message, equalTo("client error"));
    }

    @Test
    void deleteUser() {
        doReturn(Mono.empty())
                .when(userService)
                .deleteTestPlatformUser(anyString());
        webClient.mutateWith(csrf())
                 .delete()
                 .uri("/user/{username}", "tester")
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody()
                 .isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"root", "u1"})
    void deleteUserError(String username) {
        webClient.mutateWith(csrf())
                 .delete()
                 .uri("/user/{username}", username)
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody(ErrorResponse.class)
                 .value(ErrorResponse::code, equalTo(1000))
                 .value(ErrorResponse::message, equalTo("client error"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"renew", "enable", "disable", "lock", "unlock"})
    void modifyUser(String path) {
        doReturn(Mono.empty())
                .when(userService)
                .enableTestPlatformUser(anyString());
        doReturn(Mono.empty())
                .when(userService)
                .disableTestPlatformUser(anyString());
        doReturn(Mono.empty())
                .when(userService)
                .lockTestPlatformUser(anyString());
        doReturn(Mono.empty())
                .when(userService)
                .unlockTestPlatformUser(anyString());
        doReturn(Mono.empty())
                .when(userService)
                .renewTestPlatformUser(any(UserModifyRequest.class));
        var request = new UserModifyRequest("tester", LocalDateTime.now());
        webClient.mutateWith(csrf())
                 .post()
                 .uri("/user/{path}", path)
                 .bodyValue(request)
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody()
                 .isEmpty();
    }

    @ParameterizedTest
    @CsvSource({"renew, u1", "enable, u1", "disable, u1", "lock, u1", "unlock, u1", "test, tester"})
    void modifyUserError(String path, String username) {
        var request = new UserModifyRequest(username, null);
        webClient.mutateWith(csrf())
                 .post()
                 .uri("/user/{path}", path)
                 .bodyValue(request)
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody(ErrorResponse.class)
                 .value(ErrorResponse::code, equalTo(1000))
                 .value(ErrorResponse::message, equalTo("client error"));
    }

}
