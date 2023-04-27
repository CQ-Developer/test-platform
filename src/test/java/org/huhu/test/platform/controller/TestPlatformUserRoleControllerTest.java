package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.request.UserRoleCreateRequest;
import org.huhu.test.platform.model.response.ErrorResponse;
import org.huhu.test.platform.model.response.UserRoleQueryResponse;
import org.huhu.test.platform.model.vo.UserRoleDeleteVo;
import org.huhu.test.platform.service.TestPlatformUserRoleService;
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

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.DEV;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@WithMockUser
@WebFluxTest(TestPlatformUserRoleController.class)
class TestPlatformUserRoleControllerTest {

    @Autowired
    WebTestClient webClient;

    @MockBean
    TestPlatformUserRoleService userRoleService;

    @Test
    void querySelfUserRole() {
        var u1 = new UserRoleQueryResponse("r1", USER);
        var u2 = new UserRoleQueryResponse("r2", USER);
        doReturn(Flux.just(u1, u2))
                .when(userRoleService)
                .queryTestPlatformUserRole(anyString());
        webClient.get()
                 .uri("/user/role")
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBodyList(UserRoleQueryResponse.class)
                 .hasSize(2)
                 .contains(u1, u2);
    }

    @Test
    void queryUserRole() {
        var user = new UserRoleQueryResponse("USER", USER);
        var dev = new UserRoleQueryResponse("DEV", DEV);
        doReturn(Flux.just(user, dev))
                .when(userRoleService)
                .queryTestPlatformUserRole(anyString());
        webClient.get()
                 .uri("/user/role/{username}", "tester")
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBodyList(UserRoleQueryResponse.class)
                 .hasSize(2)
                 .contains(user, dev);
    }

    @Test
    void queryUserRoleError() {
        webClient.get()
                 .uri("/user/role/{username}", "u1")
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody(ErrorResponse.class)
                 .value(ErrorResponse::code, is(1000));
    }

    @Test
    void createUserRole() {
        doReturn(Mono.empty())
                .when(userRoleService)
                .createTestPlatformUserRole(any(UserRoleCreateRequest.class));
        var request = new UserRoleCreateRequest("tester", Set.of(USER));
        webClient.mutateWith(csrf())
                 .put()
                 .uri("/user/role")
                 .bodyValue(request)
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody()
                 .isEmpty();
    }

    @Test
    void deleteUserRole() {
        doReturn(Mono.empty())
                .when(userRoleService)
                .deleteTestPlatformUseRole(any(UserRoleDeleteVo.class));
        webClient.mutateWith(csrf())
                 .delete()
                 .uri("/user/role/{username}?roleLevel={role}", "tester", 1)
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody()
                 .isEmpty();
    }

    @ParameterizedTest
    @CsvSource({"u1, 1", "tester, 0"})
    void deleteUserRoleError(String username, int roleLevel) {
        webClient.mutateWith(csrf())
                 .delete()
                 .uri("/user/role/{username}?roleLevel={role}", username, roleLevel)
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody(ErrorResponse.class)
                 .value(ErrorResponse::code, is(1000));
    }

}
