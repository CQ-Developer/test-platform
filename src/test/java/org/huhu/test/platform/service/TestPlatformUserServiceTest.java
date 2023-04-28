package org.huhu.test.platform.service;

import org.huhu.test.platform.converter.test.NullValueArgumentConverter;
import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.huhu.test.platform.model.request.UserCreateRequest;
import org.huhu.test.platform.model.request.UserModifyRequest;
import org.huhu.test.platform.model.table.TestPlatformUser;
import org.huhu.test.platform.model.table.TestPlatformUserProfile;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.repository.TestPlatformUserProfileRepository;
import org.huhu.test.platform.repository.TestPlatformUserRepository;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.huhu.test.platform.repository.TestPlatformVariableRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.huhu.test.platform.constant.TestPlatformDefaultName.DEFAULT_PROFILE_NAME;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.DEV;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ImportAutoConfiguration(exclude = {SqlInitializationAutoConfiguration.class})
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
        userService
                .queryTestPlatformUser()
                .as(StepVerifier::create)
                .assertNext(i -> {
                    assertEquals("u1", i.username());
                    assertIterableEquals(List.of(USER), i.userRoles());
                })
                .assertNext(i -> {
                    assertEquals("u2", i.username());
                    assertIterableEquals(List.of(DEV), i.userRoles());
                })
                .verifyComplete();
    }

    @Test
    void testPlatformUserDetail() {
        var user = new TestPlatformUser(1L, "tester", "123456", true, false,
                LocalDateTime.of(2000, 7, 1, 1, 1), LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 1, 1));
        doReturn(Mono.just(user))
                .when(userRepository)
                .findByUsername(anyString());
        var userRole = new TestPlatformUserRole(1L, DEV, "tester");
        doReturn(Flux.just(userRole))
                .when(userRoleRepository)
                .findByUsername(anyString());
        userService
                .queryTestPlatformUserDetail("tester")
                .as(StepVerifier::create)
                .assertNext(i -> {
                    assertEquals("tester", i.username());
                    assertIterableEquals(List.of(DEV), i.roleLevels());
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
        userService
                .queryTestPlatformUserDetail("tester")
                .as(StepVerifier::create)
                .verifyError(ClientTestPlatformException.class);
    }

    @Test
    void createTestPlatformUser() {
        var user = new TestPlatformUser(0L, "tester", "123456", true, false,
                LocalDateTime.now().plusMonths(6L), LocalDateTime.now(), LocalDateTime.now().plusYears(1L));
        doReturn(Mono.just(user))
                .when(userRepository)
                .save(any(TestPlatformUser.class));
        var userRole = new TestPlatformUserRole(0L, USER, "TESTER");
        doReturn(Flux.just(userRole))
                .when(userRoleRepository)
                .saveAll(anyCollection());
        var userProfile = new TestPlatformUserProfile(0L, DEFAULT_PROFILE_NAME, "tester");
        doReturn(Mono.just(userProfile))
                .when(userProfileRepository)
                .save(any(TestPlatformUserProfile.class));
        doReturn(Mono.empty())
                .when(userRepository)
                .findByUsername(anyString());
        var request = new UserCreateRequest("tester", "123456", Set.of(USER), null);
        userService
                .createTestPlatformUser(request)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void createTestPlatformUserError() {
        doReturn(Mono.empty())
                .when(userRepository)
                .save(any(TestPlatformUser.class));
        doReturn(Flux.empty())
                .when(userRoleRepository)
                .saveAll(anyCollection());
        doReturn(Mono.empty())
                .when(userProfileRepository)
                .save(any(TestPlatformUserProfile.class));
        var user = new TestPlatformUser(0L, "tester", "123456", true, false,
                LocalDateTime.now().plusMonths(6L), LocalDateTime.now(), LocalDateTime.now().plusYears(1L));
        doReturn(Mono.just(user))
                .when(userRepository)
                .findByUsername(anyString());
        var request = new UserCreateRequest("tester", "123456", Set.of(USER), null);
        userService
                .createTestPlatformUser(request)
                .as(StepVerifier::create)
                .verifyError(ClientTestPlatformException.class);
    }

    @Test
    void deleteTestPlatformUser() {
        doReturn(Mono.just(1))
                .when(userRepository)
                .deleteByUsername(anyString());
        doReturn(Mono.just(1))
                .when(userRoleRepository)
                .deleteByUsername(anyString());
        doReturn(Mono.just(1))
                .when(userProfileRepository)
                .deleteByUsername(anyString());
        doReturn(Mono.just(1))
                .when(variableRepository)
                .deleteByUsername(anyString());
        userService
                .deleteTestPlatformUser("tester")
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @ParameterizedTest
    @ValueSource(strings = {"2000-01-01T01:01:01", "null"})
    void renewTestPlatformUser(@ConvertWith(NullValueArgumentConverter.class) LocalDateTime expiredTime) {
        System.out.println(expiredTime);
        doReturn(Mono.just(1))
                .when(userRepository)
                .setExpiredTimeFor(any(LocalDateTime.class), anyString());
        var request = new UserModifyRequest("tester", expiredTime);
        userService
                .renewTestPlatformUser(request)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void enableTestPlatformUser() {
        doReturn(Mono.just(1))
                .when(userRepository)
                .enableFor(anyString());
        userService
                .enableTestPlatformUser("tester")
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void disableTestPlatformUser() {
        doReturn(Mono.just(1))
                .when(userRepository)
                .disableFor(anyString());
        userService
                .disableTestPlatformUser("tester")
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void lockTestPlatformUser() {
        doReturn(Mono.just(1))
                .when(userRepository)
                .lockFor(anyString());
        userService
                .lockTestPlatformUser("tester")
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void unlockTestPlatformUser() {
        doReturn(Mono.just(1))
                .when(userRepository)
                .unlockFor(anyString());
        userService
                .unlockTestPlatformUser("tester")
                .as(StepVerifier::create)
                .verifyComplete();
    }

}
