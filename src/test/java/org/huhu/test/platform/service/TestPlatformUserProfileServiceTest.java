package org.huhu.test.platform.service;

import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.huhu.test.platform.model.table.TestPlatformUserProfile;
import org.huhu.test.platform.model.vo.UserProfileModifyVo;
import org.huhu.test.platform.repository.TestPlatformUserProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static reactor.test.StepVerifier.create;

@SpringBootTest
class TestPlatformUserProfileServiceTest {

    @MockBean
    TestPlatformUserProfileRepository userProfileRepository;

    @Autowired
    TestPlatformUserProfileService userProfileService;

    @Test
    void activeTestPlatformUserProfile() {
        var profile = new TestPlatformUserProfile(0L, "p1", "u1");
        doReturn(Mono.just(profile))
                .when(userProfileRepository)
                .findByUsernameAndProfileName(anyString(), anyString());
        var vo = new UserProfileModifyVo("u1", "p1");
        create(userProfileService.activeTestPlatformUserProfile(vo))
                .verifyComplete();
    }

    @Test
    void activeTestPlatformUserProfileError() {
        doReturn(Mono.empty())
                .when(userProfileRepository)
                .findByUsernameAndProfileName(anyString(), anyString());
        var vo = new UserProfileModifyVo("u1", "p1");
        create(userProfileService.activeTestPlatformUserProfile(vo))
                .verifyError(ClientTestPlatformException.class);
    }

    @Test
    void queryTestPlatformUserProfile() {
        var p1 = new TestPlatformUserProfile(0L, "default", "tester");
        var p2 = new TestPlatformUserProfile(1L, "dev", "tester");
        doReturn(Flux.just(p1, p2))
                .when(userProfileRepository)
                .findByUsername(anyString());
        create(userProfileService.queryTestPlatformUserProfile("tester"))
                .assertNext(i -> {
                    assertEquals("default", i.active());
                    assertEquals(List.of("default", "dev"), i.candidates());
                })
                .verifyComplete();
    }

    @Test
    void queryTestPlatformUserActiveProfile() {
        create(userProfileService.queryTestPlatformUserActiveProfile("tester"))
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
        create(userProfileService.createTestPlatformUserProfile(vo))
                .verifyComplete();
    }

    @Test
    void createTestPlatformUserProfileError() {
        var profile = new TestPlatformUserProfile(0L, "p1", "u1");
        doReturn(Mono.just(profile))
                .when(userProfileRepository)
                .findByUsernameAndProfileName(anyString(), anyString());
        var vo = new UserProfileModifyVo("u1", "p1");
        create(userProfileService.createTestPlatformUserProfile(vo))
                .verifyError(ClientTestPlatformException.class);
    }

    @Test
    void deleteTestPlatformUserProfile() {
        doReturn(Mono.just(1))
                .when(userProfileRepository)
                .deleteByUsernameAndProfileName(anyString(), anyString());
        var vo = new UserProfileModifyVo("u2", "p1");
        create(userProfileService.deleteTestPlatformUserProfile(vo))
                .verifyComplete();
    }

    @Test
    void deleteTestPlatformUserProfileError() {
        var vo = new UserProfileModifyVo("u1", "p1");
        create(userProfileService.deleteTestPlatformUserProfile(vo))
                .verifyError(ClientTestPlatformException.class);
    }

}
