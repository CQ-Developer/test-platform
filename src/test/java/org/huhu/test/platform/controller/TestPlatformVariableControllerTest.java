package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.request.VariableModifyRequest;
import org.huhu.test.platform.model.response.VariableQueryResponse;
import org.huhu.test.platform.model.vo.VariableCreateVo;
import org.huhu.test.platform.model.vo.VariableDeleteVo;
import org.huhu.test.platform.model.vo.VariableUpdateVo;
import org.huhu.test.platform.service.TestPlatformVariableService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

/**
 * 测试平台变量单元测试
 *
 * @author 18551681083@163.com
 * @see TestPlatformVariableController
 * @since 0.0.1
 */
@WithMockUser
@WebFluxTest(TestPlatformVariableController.class)
class TestPlatformVariableControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    TestPlatformVariableService globalVariableService;

    @Test
    void queryGlobalVariable() {
        var url = new VariableQueryResponse("url", "http://some.host", "base url");
        var header = new VariableQueryResponse("header", "some header", "base header");
        doReturn(Flux.just(url, header))
                .when(globalVariableService)
                .queryTestPlatformVariable(anyString());
        webTestClient
                .get()
                .uri("/variable/global")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(VariableQueryResponse.class).hasSize(2);
    }

    @Test
    void createGlobalVariable() {
        doReturn(Mono.empty())
                .when(globalVariableService)
                .createTestPlatformVariable(any(VariableCreateVo.class));
        var request = new VariableModifyRequest("name", "value", "description");
        webTestClient
                .mutateWith(csrf())
                .put()
                .uri("/variable/global")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @ParameterizedTest
    @CsvSource({"'', value", "n-me, value", "name, ''"})
    void createGlobalVariableInvalidParameter(String name, String value) {
        var request = new VariableModifyRequest(name, value, null);
        webTestClient
                .mutateWith(csrf())
                .put()
                .uri("/variable/global")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(1000);
    }

    @Test
    void updateGlobalVariable() {
        doReturn(Mono.empty())
                .when(globalVariableService)
                .updateTestPlatformVariable(any(VariableUpdateVo.class));
        var request = new VariableModifyRequest("name", "value", "description");
        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/variable/global/{variableName}", "name")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @ParameterizedTest
    @CsvSource({"'a-', name, value", "name, '', value", "name, 'a-', value", "name, 'name', ''"})
    void updateGlobalVariableInvalidParameter(String path, String name, String value) {
        doReturn(Mono.empty())
                .when(globalVariableService)
                .updateTestPlatformVariable(any(VariableUpdateVo.class));
        var request = new VariableModifyRequest(name, value, null);
        webTestClient
                .mutateWith(csrf())
                .post()
                .uri("/variable/global/{variableName}", path)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(1000);
    }

    @Test
    void deleteGlobalVariable() {
        doReturn(Mono.empty())
                .when(globalVariableService)
                .deleteTestPlatformVariable(any(VariableDeleteVo.class));
        webTestClient
                .mutateWith(csrf())
                .delete()
                .uri("/variable/global/{variableName}", "name")
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @Test
    void deleteGlobalVariableInvalidParameter() {
        doReturn(Mono.empty())
                .when(globalVariableService)
                .deleteTestPlatformVariable(any(VariableDeleteVo.class));
        webTestClient
                .mutateWith(csrf())
                .delete()
                .uri("/variable/global/{variableName}", "a-")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(1000);
    }

}
