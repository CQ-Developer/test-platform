package org.huhu.test.platform.controller;

import org.hamcrest.core.IsEqual;
import org.huhu.test.platform.model.request.UserCreateRequest;
import org.huhu.test.platform.model.response.ErrorResponse;
import org.huhu.test.platform.model.response.UserDetailQueryResponse;
import org.huhu.test.platform.model.response.UserQueryResponse;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsIterableContaining.hasItem;
import static org.hamcrest.core.IsIterableContaining.hasItems;
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
                 .value(hasItems(u1, u2));
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
                 .value(UserDetailQueryResponse::username, equalTo("jack"))
                 .value(UserDetailQueryResponse::roleLevels, hasItem(USER))
                 .value(UserDetailQueryResponse::enabled, equalTo(true))
                 .value(UserDetailQueryResponse::locked, equalTo(false))
                 .value(UserDetailQueryResponse::registerTime, equalTo(now))
                 .value(UserDetailQueryResponse::expiredTime, equalTo(now.plusDays(1L)));
    }

    @Test
    void testQueryUserError() {
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
    void createUser() {
        doReturn(Mono.empty())
                .when(userService)
                .createTestPlatformUser(any(UserCreateRequest.class));
        var request = new UserCreateRequest("tester", "123456", List.of(USER), LocalDateTime.now());
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
                 .value(ErrorResponse::code, IsEqual.equalTo(1000))
                 .value(ErrorResponse::message, IsEqual.equalTo("client error"));
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