package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.request.VariableCreateRequest;
import org.huhu.test.platform.model.request.VariableUpdateRequest;
import org.huhu.test.platform.model.response.ErrorResponse;
import org.huhu.test.platform.model.response.VariableQueryResponse;
import org.huhu.test.platform.model.vo.VariableCreateVo;
import org.huhu.test.platform.model.vo.VariableQueryVo;
import org.huhu.test.platform.model.vo.VariableUpdateVo;
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

import static org.hamcrest.Matchers.is;
import static org.huhu.test.platform.constant.TestPlatformVariableScope.GLOBAL;
import static org.huhu.test.platform.constant.TestPlatformVariableScope.SUITE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

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
        doReturn(Mono.just("default"))
                .when(userProfileService)
                .queryTestPlatformUserActiveProfile(anyString());
        var v1 = new VariableQueryResponse("v1", "value1", GLOBAL, "desc1");
        var v2 = new VariableQueryResponse("v2", "value2", SUITE, "desc2");
        doReturn(Flux.just(v1, v2))
                .when(variableService)
                .queryTestPlatformVariable(any(VariableQueryVo.class));
        webTestClient.get()
                     .uri("/variable/{variableName}", "test")
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBodyList(VariableQueryResponse.class)
                     .hasSize(2)
                     .contains(v1, v2);
    }

    @Test
    void queryVariableError() {
        webTestClient.get()
                     .uri("/variable/{variableName}", ".@#")
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody(ErrorResponse.class)
                     .value(ErrorResponse::code, is(1000));
    }

    @Test
    void createVariable() {
        doReturn(Mono.just("default"))
                .when(userProfileService)
                .queryTestPlatformUserActiveProfile(anyString());
        doReturn(Mono.empty())
                .when(variableService)
                .createTestPlatformVariable(any(VariableCreateVo.class));
        var request = new VariableCreateRequest("name", "value", GLOBAL, "test");
        webTestClient.mutateWith(csrf())
                     .put()
                     .uri("/variable")
                     .bodyValue(request)
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody()
                     .isEmpty();
    }

    @Test
    void createVariableError() {
        doReturn(Mono.just("default"))
                .when(userProfileService)
                .queryTestPlatformUserActiveProfile(anyString());
        var request = new VariableCreateRequest("", "", null, "");
        webTestClient.mutateWith(csrf())
                     .put()
                     .uri("/variable")
                     .bodyValue(request)
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody(ErrorResponse.class)
                     .value(ErrorResponse::code, is(1000));
    }

    @Test
    void updateVariable() {
        doReturn(Mono.just("default"))
                .when(userProfileService)
                .queryTestPlatformUserActiveProfile(anyString());
        doReturn(Mono.empty())
                .when(variableService)
                .updateTestPlatformVariable(any(VariableUpdateVo.class));
        var request = new VariableUpdateRequest("value", "test");
        webTestClient.mutateWith(csrf())
                     .post()
                     .uri("/variable/{variableName}?variableScope={variableScope}", "name", 4)
                     .bodyValue(request)
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody()
                     .isEmpty();
    }

    @Test
    void updateVariableError() {
        var request = new VariableUpdateRequest("", null);
        webTestClient.mutateWith(csrf())
                     .post()
                     .uri("/variable/{variableName}?variableScope={variableScope}", "#a", 8)
                     .bodyValue(request)
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody(ErrorResponse.class)
                     .value(ErrorResponse::code, is(1000));
    }

    @Test
    void deleteVariable() {
    }

}
