package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.request.GlobalVariableModifyRequest;
import org.huhu.test.platform.model.vo.GlobalVariableCreateVo;
import org.huhu.test.platform.service.TestPlatformGlobalVariableService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WithMockUser
@WebFluxTest(TestPlatformGlobalVariableController.class)
class TestPlatformGlobalVariableControllerTest {

    @MockBean
    TestPlatformGlobalVariableService globalVariableService;

    @Autowired
    WebTestClient webClient;

    @Test
    void query() {
    }

    @Test
    void create() {
        Mockito.doReturn(Mono.empty())
               .when(globalVariableService)
               .createTestPlatformGlobalVariable(ArgumentMatchers.any(GlobalVariableCreateVo.class));
        var request = new GlobalVariableModifyRequest("someName", "someValue", "someDescription");
        webClient.mutateWith(SecurityMockServerConfigurers.csrf())
                 .put()
                 .uri("/global/variable")
                 .bodyValue(request)
                 .exchange()
                 .expectStatus().isOk();
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}