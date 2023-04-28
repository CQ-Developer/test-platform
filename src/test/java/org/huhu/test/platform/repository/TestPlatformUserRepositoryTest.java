package org.huhu.test.platform.repository;

import org.huhu.test.platform.model.table.TestPlatformUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

@DataR2dbcTest
class TestPlatformUserRepositoryTest {

    @Autowired
    TestPlatformUserRepository userRepository;

    @Test
    void findByUsername() {
        userRepository
                .findByUsername("jack")
                .doOnNext(System.out::println)
                .map(TestPlatformUser::username)
                .map("jack"::equals)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void deleteByUsername() {
        userRepository
                .deleteByUsername("rose")
                .map(Integer.valueOf(1)::equals)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void setExpiredTimeFor() {
        userRepository
                .setExpiredTimeFor(LocalDateTime.of(2023, 4, 28, 14, 35), "jack")
                .then(userRepository.findByUsername("jack"))
                .map(TestPlatformUser::expiredTime)
                .map(LocalDateTime.of(2023, 4, 28, 14, 35)::equals)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void setPasswordTimeFor() {
        userRepository
                .setPasswordTimeFor(LocalDateTime.of(2023, 4, 28, 14, 35), "jack")
                .then(userRepository.findByUsername("jack"))
                .map(TestPlatformUser::passwordTime)
                .map(LocalDateTime.of(2023, 4, 28, 14, 35)::equals)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void enableFor() {
        userRepository
                .enableFor("jack")
                .then(userRepository.findByUsername("jack"))
                .map(TestPlatformUser::enabled)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void disableFor() {
        userRepository
                .disableFor("jack")
                .then(userRepository.findByUsername("jack"))
                .map(TestPlatformUser::enabled)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertFalse)
                .verifyComplete();
    }

    @Test
    void lockFor() {
        userRepository
                .lockFor("jack")
                .then(userRepository.findByUsername("jack"))
                .map(TestPlatformUser::locked)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void unlockFor() {
        userRepository
                .unlockFor("jack")
                .then(userRepository.findByUsername("jack"))
                .map(TestPlatformUser::locked)
                .as(StepVerifier::create)
                .assertNext(Assertions::assertFalse)
                .verifyComplete();
    }

}
