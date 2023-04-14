package org.huhu.test.platform.controller;

import org.huhu.test.platform.constant.TestPlatformRoleName;
import org.huhu.test.platform.model.request.UserRoleCreateRequest;
import org.huhu.test.platform.service.TestPlatformUserRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.huhu.test.platform.constant.TestPlatformRoleName.DEV;
import static org.huhu.test.platform.constant.TestPlatformRoleName.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

/**
 * 测试平台用户角色单元测试
 *
 * @author 18551681083@163.com
 * @see TestPlatformUserRoleController
 * @since 0.0.1
 */
@WithMockUser
@WebFluxTest(TestPlatformUserRoleController.class)
class TestPlatformUserRoleControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    TestPlatformUserRoleService userRoleService;

    @Test
    void queryUserRole() {
        doReturn(Flux.just(USER, DEV))
                .when(userRoleService)
                .queryTestPlatformUserRole(anyString());
        webTestClient
                .get()
                .uri("/management/role")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TestPlatformRoleName.class).hasSize(2);
    }

    @Test
    void createUserRole() {
        doReturn(Mono.empty())
                .when(userRoleService)
                .createTestPlatformUserRole(any(UserRoleCreateRequest.class));
        var request = new UserRoleCreateRequest("Jack", USER);
        webTestClient
                .mutateWith(csrf())
                .put()
                .uri("/management/role")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void deleteUserRole() {
        webTestClient
                .mutateWith(csrf())
                .delete()
                .uri("/management/role/{roleName}?username={username}", "USER", "Jack")
                .exchange()
                .expectStatus().isOk();
    }

}