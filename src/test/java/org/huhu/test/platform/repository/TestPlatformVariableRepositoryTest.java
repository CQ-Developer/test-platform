package org.huhu.test.platform.repository;

import org.huhu.test.platform.configuration.TestPlatformR2dbcConfiguration;
import org.huhu.test.platform.model.table.TestPlatformVariable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;

import static org.huhu.test.platform.constant.TestPlatformVariableScope.GLOBAL;
import static org.huhu.test.platform.constant.TestPlatformVariableScope.GROUP;
import static org.junit.jupiter.api.Assertions.assertEquals;

// todo 待完成
@DataR2dbcTest
@Import(TestPlatformR2dbcConfiguration.class)
class TestPlatformVariableRepositoryTest {

    @Autowired
    TestPlatformVariableRepository variableRepository;

    @Test
    void findByUsernameAndVariableProfile() {
        variableRepository
                .findByUsernameAndVariableProfile("jack", "default")
                .map(TestPlatformVariable::variableName)
                .as(StepVerifier::create)
                .verifyComplete();
//                .assertNext(i -> assertEquals("base_url", i))
//                .assertNext(i -> assertEquals("base_url", i))
//                .assertNext(i -> assertEquals("app_name", i))
//                .verifyComplete();
    }

    @Test
    void findByUsernameAndVariableProfileAndVariableName() {
        variableRepository
                .findByUsernameAndVariableProfileAndVariableName("jack", "default", "base_url")
                .map(TestPlatformVariable::variableScope)
                .as(StepVerifier::create)
                .assertNext(i -> assertEquals(GLOBAL, i))
                .assertNext(i -> assertEquals(GROUP, i))
                .verifyComplete();
    }

    @Test
    void existsByUsernameAndVariableProfileAndVariableNameAndVariableScope() {
    }

    @Test
    void setVariableValueAndVariableDescriptionFor() {
    }

    @Test
    void deleteByUsername() {
    }

    @Test
    void deleteByUsernameAndVariableProfileAndAndVariableNameAndVariableScope() {
    }

}