package org.huhu.test.platform.repository;

import org.huhu.test.platform.model.table.TestPlatformUserProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.test.StepVerifier;

@DataR2dbcTest
class TestPlatformUserProfileRepositoryTest {

    @Autowired
    TestPlatformUserProfileRepository userProfileRepository;

    @Test
    void findByUsername() {
        userProfileRepository
                .findByUsername("jack")
                .map(TestPlatformUserProfile::username)
                .map("jack"::equals)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void findByUsernameAndProfileName() {
        userProfileRepository
                .findByUsernameAndProfileName("jack", "default")
                .map(TestPlatformUserProfile::profileName)
                .map("default"::equals)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void deleteByUsername() {
        userProfileRepository
                .deleteByUsername("rose")
                .map(Integer.valueOf(1)::equals)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void deleteByUsernameAndProfileName() {
        userProfileRepository
                .deleteByUsernameAndProfileName("jack", "dev")
                .map(Integer.valueOf(1)::equals)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

}
