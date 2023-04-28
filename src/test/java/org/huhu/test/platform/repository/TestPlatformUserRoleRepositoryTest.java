package org.huhu.test.platform.repository;

import org.huhu.test.platform.configuration.TestPlatformR2dbcConfiguration;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;

import static org.huhu.test.platform.constant.TestPlatformRoleLevel.ADMIN;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.DEV;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataR2dbcTest
@Import(TestPlatformR2dbcConfiguration.class)
class TestPlatformUserRoleRepositoryTest {

    @Autowired
    TestPlatformUserRoleRepository userRoleRepository;

    @Test
    void findByUsername() {
        userRoleRepository
                .findByUsername("jack")
                .map(TestPlatformUserRole::roleLevel)
                .as(StepVerifier::create)
                .assertNext(i -> assertEquals(USER, i))
                .assertNext(i -> assertEquals(DEV, i))
                .assertNext(i -> assertEquals(ADMIN, i))
                .verifyComplete();
    }

    @Test
    void countByUsername() {
        userRoleRepository
                .countByUsername("jack")
                .map(Integer.valueOf(3)::equals)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void deleteByUsername() {
        userRoleRepository
                .deleteByUsername("rose")
                .map(Integer.valueOf(3)::equals)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void deleteByUsernameAndRoleLevel() {
        userRoleRepository
                .deleteByUsernameAndRoleLevel("lucy", USER)
                .map(Integer.valueOf(1)::equals)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

}
