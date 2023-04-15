package org.huhu.test.platform.controller;

import cn.hutool.core.util.StrUtil;
import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import org.huhu.test.platform.model.request.UserCreateRequest;
import org.huhu.test.platform.model.request.UserRenewRequest;
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
import java.util.stream.Stream;

import static org.huhu.test.platform.constant.TestPlatformRoleLevel.ADMIN;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.DEV;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

/**
 * 测试平台全局变量单元测试
 *
 * @author 18551681083@163.com
 * @see TestPlatformUserController
 * @since 0.0.1
 */
@WithMockUser
@WebFluxTest(TestPlatformUserController.class)
class TestPlatformUserControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    TestPlatformUserService userService;

    @Test
    void queryUser() {
        var jack = new UserQueryResponse("Jack", List.of(DEV, ADMIN));
        var lucy = new UserQueryResponse("Lucy", List.of(USER, DEV));
        var john = new UserQueryResponse("John", List.of(USER));
        doReturn(Flux.just(jack, lucy, john))
                .when(userService)
                .queryTestPlatformUser();
        webTestClient
                .get()
                .uri("/management/user")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserQueryResponse.class).hasSize(3);
    }

    @Test
    void queryUserDetail() {
        var registerTime = LocalDateTime.of(2023, 4, 13, 0, 0);
        var expiredTime = registerTime.plusYears(1L);
        var jack = new UserDetailQueryResponse("Jack", List.of(DEV, ADMIN),
                true, false, registerTime, expiredTime);
        doReturn(Mono.just(jack))
                .when(userService)
                .queryTestPlatformUser(anyString());
        webTestClient
                .get()
                .uri("/management/user/{username}", "jack")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.username").isEqualTo("Jack")
                .jsonPath("$.roleLevels[0]").isEqualTo(2)
                .jsonPath("$.roleLevels[1]").isEqualTo(3)
                .jsonPath("$.enabled").isEqualTo(true)
                .jsonPath("$.locked").isEqualTo(false)
                .jsonPath("$.registerTime").isEqualTo("2023-04-13T00:00:00")
                .jsonPath("$.expiredTime").isEqualTo("2024-04-13T00:00:00");
    }

    @Test
    void queryUserDetailInvalidParameter() {
        webTestClient
                .get()
                .uri("/management/user/{username}", "n-")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(1000);
    }

    @Test
    void createUser() {
        doReturn(Mono.empty())
                .when(userService)
                .createTestPlatformUser(any(UserCreateRequest.class));
        var request = new UserCreateRequest("Jack", "Jack_123", List.of(USER, DEV));
        webTestClient
                .mutateWith(csrf())
                .put()
                .uri("/management/user")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @ParameterizedTest
    @CsvSource({"'', Jack_123, 'USER,DEV'", "'J-', Jack_123, 'USER,DEV'",
            "Jack, '', 'USER,DEV'", "Jack, 'J^ck_123', 'USER,DEV'",
            "Jack, Jack_123, ''"})
    void createUserInvalidParameter(String name, String pass, String role) {
        var roleLevels = Stream
                .of(role.split(","))
                .filter(StrUtil::isNotBlank)
                .map(TestPlatformRoleLevel::valueOf)
                .toList();
        var request = new UserCreateRequest(name, pass, roleLevels);
        webTestClient
                .mutateWith(csrf())
                .put()
                .uri("/management/user")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(1000);
    }

    @Test
    void deleteUser() {
        doReturn(Mono.empty())
                .when(userService)
                .deleteTestPlatformUser(anyString());
        webTestClient
                .mutateWith(csrf())
                .delete()
                .uri("/management/user/{username}", "Jack")
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @Test
    void deleteUserRoot() {
        doReturn(Mono.empty())
                .when(userService)
                .deleteTestPlatformUser(anyString());
        webTestClient
                .mutateWith(csrf())
                .delete()
                .uri("/management/user/{username}", "root")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(1000);
    }

    @Test
    void deleteUserInvalidParameter() {
        doReturn(Mono.empty())
                .when(userService)
                .deleteTestPlatformUser(anyString());
        webTestClient
                .mutateWith(csrf())
                .delete()
                .uri("/management/user/{username}", "J-")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(1000);
    }

    @Test
    void renewUser() {
        doReturn(Mono.empty())
                .when(userService)
                .renewTestPlatformUser(any(UserRenewRequest.class));
        var request = new UserRenewRequest("Jack", LocalDateTime.now());
        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/management/user/renew")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @Test
    void renewUserInvalidParameter() {
        var request = new UserRenewRequest("J-", LocalDateTime.now());
        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/management/user/renew")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code", 1000);
    }

    @ParameterizedTest
    @ValueSource(strings = {"enable", "disable", "lock", "unlock"})
    void testEnableAndLock(String path) {
        doReturn(Mono.empty())
                .when(userService)
                .enableTestPlatformUser(anyString());
        webTestClient
                .get()
                .uri("/management/user/{path}?username={username}", path, "Jack")
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"enable", "disable", "lock", "unlock"})
    void testEnableAndLockInvalidParameter(String path) {
        doReturn(Mono.empty())
                .when(userService)
                .enableTestPlatformUser(anyString());
        webTestClient
                .get()
                .uri("/management/user/{path}?username={username}", path, "J-")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(1000);
    }

}
