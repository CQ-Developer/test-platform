package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.response.VariableQueryResponse;
import org.huhu.test.platform.model.vo.VariablesQueryVo;
import org.huhu.test.platform.service.TestPlatformUserProfileService;
import org.huhu.test.platform.service.TestPlatformVariableService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.huhu.test.platform.constant.TestPlatformVariableScope.GLOBAL;
import static org.huhu.test.platform.constant.TestPlatformVariableScope.SUITE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@WithMockUser
@WebFluxTest(TestPlatformVariableController.class)
class TestPlatformVariableControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    TestPlatformVariableService variableService;

    @MockBean
    TestPlatformUserProfileService userProfileService;

    @Test
    void queryVariables() {
        doReturn(Mono.just("default"))
                .when(userProfileService)
                .queryTestPlatformUserActiveProfile(anyString());
        var v1 = new VariableQueryResponse("v1", "value1", GLOBAL, "desc1");
        var v2 = new VariableQueryResponse("v2", "value2", SUITE, "desc2");
        doReturn(Flux.just(v1, v2))
                .when(variableService)
                .queryTestPlatformVariables(any(VariablesQueryVo.class));
        webTestClient.get()
                     .uri("/variable")
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBodyList(VariableQueryResponse.class)
                     .hasSize(2)
                     .contains(v1, v2);
    }

    @Test
    void queryVariable() {
    }

    @Test
    void createVariable() {
    }

    @Test
    void updateVariable() {
    }

    @Test
    void deleteVariable() {
    }

}
