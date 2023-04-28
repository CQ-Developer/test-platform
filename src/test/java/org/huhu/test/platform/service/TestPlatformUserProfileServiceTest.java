package org.huhu.test.platform.service;

import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.huhu.test.platform.model.table.TestPlatformUserProfile;
import org.huhu.test.platform.model.vo.UserProfileModifyVo;
import org.huhu.test.platform.repository.TestPlatformUserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class TestPlatformUserProfileServiceTest {

    @Autowired
    TestPlatformUserProfileService userProfileService;

    @MockBean
    TestPlatformUserProfileRepository userProfileRepository;

    @MockBean
    ReactiveRedisTemplate<Object, Object> redisTemplate;

    @Mock
    ReactiveValueOperations<Object, Object> valueOperations;

    @BeforeEach
    void beforeEach() {
        doReturn(valueOperations)
                .when(redisTemplate)
                .opsForValue();
    }

    @Test
    void activeTestPlatformUserProfile() {
        var profile = new TestPlatformUserProfile(0L, "p1", "u1");
        doReturn(Mono.just(profile))
                .when(userProfileRepository)
                .findByUsernameAndProfileName(anyString(), anyString());
        doReturn(Mono.just(true))
                .when(valueOperations)
                .set(anyString(), anyString(), any(Duration.class));
        var vo = new UserProfileModifyVo("u1", "p1");
        userProfileService
                .activeTestPlatformUserProfile(vo)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void activeTestPlatformUserProfileError() {
        doReturn(Mono.empty())
                .when(userProfileRepository)
                .findByUsernameAndProfileName(anyString(), anyString());
        var vo = new UserProfileModifyVo("u1", "p1");
        userProfileService
                .activeTestPlatformUserProfile(vo)
                .as(StepVerifier::create)
                .verifyError(ClientTestPlatformException.class);
    }

    @Test
    void queryTestPlatformUserProfile() {
        var p1 = new TestPlatformUserProfile(0L, "default", "tester");
        var p2 = new TestPlatformUserProfile(1L, "dev", "tester");
        doReturn(Flux.just(p1, p2))
                .when(userProfileRepository)
                .findByUsername(anyString());
        doReturn(Mono.just("default"))
                .when(valueOperations)
                .get(anyString());
        userProfileService
                .queryTestPlatformUserProfile("tester")
                .as(StepVerifier::create)
                .assertNext(i -> {
                    assertEquals("default", i.active());
                    assertIterableEquals(List.of("default", "dev"), i.candidates());
                })
                .verifyComplete();
    }

    @Test
    void queryTestPlatformUserActiveProfile() {
        doReturn(Mono.empty())
                .when(valueOperations)
                .get(anyString());
        doReturn(Mono.just(true))
                .when(valueOperations)
                .set(anyString(), anyString(), any(Duration.class));
        userProfileService
                .queryTestPlatformUserActiveProfile("tester")
                .as(StepVerifier::create)
                .assertNext(i -> assertEquals("default", i))
                .verifyComplete();
    }

    @Test
    void createTestPlatformUserProfile() {
        doReturn(Mono.empty())
                .when(userProfileRepository)
                .findByUsernameAndProfileName(anyString(), anyString());
        var profile = new TestPlatformUserProfile(0L, "p1", "u1");
        doReturn(Mono.just(profile))
                .when(userProfileRepository)
                .save(any(TestPlatformUserProfile.class));
        var vo = new UserProfileModifyVo("u1", "p1");
        userProfileService
                .createTestPlatformUserProfile(vo)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void createTestPlatformUserProfileError() {
        var profile = new TestPlatformUserProfile(0L, "p1", "u1");
        doReturn(Mono.just(profile))
                .when(userProfileRepository)
                .findByUsernameAndProfileName(anyString(), anyString());
        var vo = new UserProfileModifyVo("u1", "p1");
        userProfileService
                .createTestPlatformUserProfile(vo)
                .as(StepVerifier::create)
                .verifyError(ClientTestPlatformException.class);
    }

    @Test
    void deleteTestPlatformUserProfile() {
        doReturn(Mono.just("p2"))
                .when(valueOperations)
                .get(anyString());
        doReturn(Mono.just(1))
                .when(userProfileRepository)
                .deleteByUsernameAndProfileName(anyString(), anyString());
        var vo = new UserProfileModifyVo("u1", "p1");
        userProfileService
                .deleteTestPlatformUserProfile(vo)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void deleteTestPlatformUserProfileError() {
        doReturn(Mono.just("p1"))
                .when(valueOperations)
                .get(anyString());
        var vo = new UserProfileModifyVo("u1", "p1");
        userProfileService
                .deleteTestPlatformUserProfile(vo)
                .as(StepVerifier::create)
                .verifyError(ClientTestPlatformException.class);
    }

}
