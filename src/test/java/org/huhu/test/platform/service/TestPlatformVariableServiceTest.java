package org.huhu.test.platform.service;

import org.huhu.test.platform.constant.TestPlatformVariableScope;
import org.huhu.test.platform.model.request.VariableCreateRequest;
import org.huhu.test.platform.model.request.VariableUpdateRequest;
import org.huhu.test.platform.model.response.VariableQueryResponse;
import org.huhu.test.platform.model.table.TestPlatformVariable;
import org.huhu.test.platform.model.vo.VariableCreateVo;
import org.huhu.test.platform.model.vo.VariableDeleteVo;
import org.huhu.test.platform.model.vo.VariableQueryVo;
import org.huhu.test.platform.model.vo.VariableUpdateVo;
import org.huhu.test.platform.model.vo.VariablesQueryVo;
import org.huhu.test.platform.repository.TestPlatformVariableRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.huhu.test.platform.constant.TestPlatformVariableScope.CASE;
import static org.huhu.test.platform.constant.TestPlatformVariableScope.GLOBAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ImportAutoConfiguration(exclude = {SqlInitializationAutoConfiguration.class})
class TestPlatformVariableServiceTest {

    @Autowired
    TestPlatformVariableService variableService;

    @MockBean
    TestPlatformVariableRepository variableRepository;

    @Test
    void queryTestPlatformVariables() {
        var v1 = new TestPlatformVariable(0L, "v1", "a", GLOBAL, "default", "d1", "tester");
        var v2 = new TestPlatformVariable(1L, "v2", "b", CASE, "default", "d2", "tester");
        doReturn(Flux.just(v1, v2))
                .when(variableRepository)
                .findByUsernameAndVariableProfile(anyString(), anyString());
        var vo = new VariablesQueryVo("tester", "default");
        variableService
                .queryTestPlatformVariables(vo)
                .as(StepVerifier::create)
                .assertNext(i -> assertEquals(new VariableQueryResponse("v1", "a", GLOBAL, "d1"), i))
                .assertNext(i -> assertEquals(new VariableQueryResponse("v2", "b", CASE, "d2"), i))
                .verifyComplete();
    }

    @Test
    void queryTestPlatformVariable() {
        var v1 = new TestPlatformVariable(0L, "v1", "a", GLOBAL, "default", "d1", "tester");
        var v2 = new TestPlatformVariable(1L, "v1", "b", CASE, "default", "d2", "tester");
        doReturn(Flux.just(v1, v2))
                .when(variableRepository)
                .findByUsernameAndVariableProfileAndVariableName(anyString(), anyString(), anyString());
        var vo = new VariableQueryVo("tester", "default", "v1");
        variableService
                .queryTestPlatformVariable(vo)
                .as(StepVerifier::create)
                .assertNext(i -> assertEquals(new VariableQueryResponse("v1", "a", GLOBAL, "d1"), i))
                .assertNext(i -> assertEquals(new VariableQueryResponse("v1", "b", CASE, "d2"), i))
                .verifyComplete();
    }

    @Test
    void createTestPlatformVariable() {
        doReturn(Mono.just(false))
                .when(variableRepository)
                .existsByUsernameAndVariableProfileAndVariableNameAndVariableScope(
                        anyString(), anyString(), anyString(), any(TestPlatformVariableScope.class));
        var v1 = new TestPlatformVariable(0L, "v1", "a", GLOBAL, "default", "d1", "tester");
        doReturn(Mono.just(v1))
                .when(variableRepository)
                .save(any(TestPlatformVariable.class));
        var vo = new VariableCreateVo("tester", "default",
                new VariableCreateRequest("v1", "a", GLOBAL, "d1"));
        variableService
                .createTestPlatformVariable(vo)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void updateTestPlatformVariable() {
        doReturn(Mono.just(1))
                .when(variableRepository)
                .setVariableValueAndVariableDescriptionFor(anyString(), anyString(),
                        anyString(), anyString(), anyString(), any(TestPlatformVariableScope.class));
        var vo = new VariableUpdateVo("tester", "default", "v1", GLOBAL,
                new VariableUpdateRequest("content", "description"));
        variableService
                .updateTestPlatformVariable(vo)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void deleteTestPlatformVariable() {
        doReturn(Mono.just(1))
                .when(variableRepository)
                .deleteByUsernameAndVariableProfileAndAndVariableNameAndVariableScope(
                        anyString(), anyString(), anyString(), any(TestPlatformVariableScope.class));
        var vo = new VariableDeleteVo("tester", "default", "v1", GLOBAL);
        variableService
                .deleteTestPlatformVariable(vo)
                .as(StepVerifier::create)
                .verifyComplete();
    }

}
