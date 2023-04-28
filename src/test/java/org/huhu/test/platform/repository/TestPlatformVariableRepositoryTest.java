package org.huhu.test.platform.repository;

import org.huhu.test.platform.configuration.TestPlatformR2dbcConfiguration;
import org.huhu.test.platform.model.table.TestPlatformVariable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;

import java.util.List;

import static org.huhu.test.platform.constant.TestPlatformVariableScope.GLOBAL;
import static org.huhu.test.platform.constant.TestPlatformVariableScope.GROUP;

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
                .map(List.of("base_url", "app_name")::contains)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .assertNext(Assertions::assertTrue)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void findByUsernameAndVariableProfileAndVariableName() {
        variableRepository
                .findByUsernameAndVariableProfileAndVariableName("jack", "default", "base_url")
                .map(TestPlatformVariable::variableScope)
                .map(List.of(GLOBAL, GROUP)::contains)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void existsByUsernameAndVariableProfileAndVariableNameAndVariableScope() {
        variableRepository
                .existsByUsernameAndVariableProfileAndVariableNameAndVariableScope("jack", "default", "app_name", GLOBAL)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void setVariableValueAndVariableDescriptionFor() {
        variableRepository
                .setVariableValueAndVariableDescriptionFor(
                        "demo-app", "nothing", "jack", "default", "app_name", GLOBAL)
                .map(Integer.valueOf(1)::equals)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void deleteByUsername() {
        variableRepository
                .deleteByUsername("rose")
                .map(Integer.valueOf(1)::equals)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void deleteByUsernameAndVariableProfileAndAndVariableNameAndVariableScope() {
        variableRepository
                .deleteByUsernameAndVariableProfileAndAndVariableNameAndVariableScope(
                        "lucy", "default", "name", GLOBAL)
                .map(Integer.valueOf(1)::equals)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

}
