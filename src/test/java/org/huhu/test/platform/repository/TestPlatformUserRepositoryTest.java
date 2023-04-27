package org.huhu.test.platform.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static reactor.test.StepVerifier.create;

// todo 要设置自动回滚
@DataR2dbcTest
class TestPlatformUserRepositoryTest {

    @Autowired
    TestPlatformUserRepository userRepository;

    @Test
    void findByUsername() {
        create(userRepository.findByUsername("root"))
                .assertNext(i -> assertEquals("root", i.username()))
                .verifyComplete();
    }

    @Test
    void deleteByUsername() {
        create(userRepository.deleteByUsername("chen"))
                .assertNext(i -> assertEquals(1, i))
                .verifyComplete();
    }

    @Test
    void setExpiredTimeFor() {
    }

    @Test
    void setPasswordTimeFor() {
    }

    @Test
    void enableFor() {
    }

    @Test
    void disableFor() {
    }

    @Test
    void lockFor() {
    }

    @Test
    void unlockFor() {
    }

}
