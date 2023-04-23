package org.huhu.test.platform.service;

import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.huhu.test.platform.model.table.TestPlatformUser;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.repository.TestPlatformUserProfileRepository;
import org.huhu.test.platform.repository.TestPlatformUserRepository;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.huhu.test.platform.repository.TestPlatformVariableRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.huhu.test.platform.constant.TestPlatformRoleLevel.DEV;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static reactor.test.StepVerifier.create;

@SpringBootTest
class TestPlatformUserServiceTest {

    @Autowired
    TestPlatformUserService userService;

    @MockBean
    TestPlatformUserRepository userRepository;

    @MockBean
    TestPlatformUserRoleRepository userRoleRepository;

    @MockBean
    TestPlatformUserProfileRepository userProfileRepository;

    @MockBean
    TestPlatformVariableRepository variableRepository;

    @Test
    void queryTestPlatformUser() {
        var u1 = new TestPlatformUserRole(0L, USER, "u1");
        var u2 = new TestPlatformUserRole(1L, DEV, "u2");
        doReturn(Flux.just(u1, u2))
                .when(userRoleRepository)
                .findAll();
        create(userService.queryTestPlatformUser())
                .assertNext(i -> {
                    assertEquals("u1", i.username());
                    assertEquals(List.of(USER), i.userRoles());
                })
                .assertNext(i -> {
                    assertEquals("u2", i.username());
                    assertEquals(List.of(DEV), i.userRoles());
                })
                .verifyComplete();
    }

    @Test
    void testPlatformUserDetail() {
        var user = new TestPlatformUser(1L, "tester", "123456", true, false,
                LocalDateTime.of(2000, 1, 1, 1, 1),
                LocalDateTime.of(2001, 1, 1, 1, 1));
        doReturn(Mono.just(user))
                .when(userRepository)
                .findByUsername(anyString());
        var userRole = new TestPlatformUserRole(1L, DEV, "tester");
        doReturn(Flux.just(userRole))
                .when(userRoleRepository)
                .findByUsername(anyString());
        create(userService.queryTestPlatformUserDetail("tester"))
                .assertNext(i -> {
                    assertEquals("tester", i.username());
                    assertEquals(List.of(DEV), i.roleLevels());
                    assertTrue(i.enabled());
                    assertFalse(i.locked());
                    assertEquals(LocalDateTime.of(2000, 1, 1, 1, 1), i.registerTime());
                    assertEquals(LocalDateTime.of(2001, 1, 1, 1, 1), i.expiredTime());
                })
                .verifyComplete();
    }

    @Test
    void testPlatformUserDetailError() {
        doReturn(Mono.empty())
                .when(userRepository)
                .findByUsername(anyString());
        doReturn(Flux.empty())
                .when(userRoleRepository)
                .findByUsername(anyString());
        create(userService.queryTestPlatformUserDetail("tester"))
                .verifyError(ClientTestPlatformException.class);
    }

    @Test
    void createTestPlatformUser() {
    }

    @Test
    void deleteTestPlatformUser() {
    }

    @Test
    void renewTestPlatformUser() {
    }

    @Test
    void enableTestPlatformUser() {
    }

    @Test
    void disableTestPlatformUser() {
    }

    @Test
    void lockTestPlatformUser() {
    }

    @Test
    void unlockTestPlatformUser() {
    }

}