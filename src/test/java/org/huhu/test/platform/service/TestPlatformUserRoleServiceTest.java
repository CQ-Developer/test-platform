package org.huhu.test.platform.service;

import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.huhu.test.platform.model.request.UserRoleCreateRequest;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.model.vo.UserRoleDeleteVo;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Set;

import static org.huhu.test.platform.constant.TestPlatformRoleLevel.DEV;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ImportAutoConfiguration(exclude = {SqlInitializationAutoConfiguration.class})
class TestPlatformUserRoleServiceTest {

    @Autowired
    TestPlatformUserRoleService userRoleService;

    @MockBean
    TestPlatformUserRoleRepository userRoleRepository;

    @Test
    void deleteTestPlatformUseRole() {
        doReturn(Mono.just(2))
                .when(userRoleRepository)
                .countByUsername(anyString());
        doReturn(Mono.just(1))
                .when(userRoleRepository)
                .deleteByUsernameAndRoleLevel(anyString(), any(TestPlatformRoleLevel.class));
        var vo = new UserRoleDeleteVo("someone", USER);
        userRoleService
                .deleteTestPlatformUseRole(vo)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void deleteTestPlatformUserRoleError() {
        doReturn(Mono.just(1))
                .when(userRoleRepository)
                .countByUsername(anyString());
        var vo = new UserRoleDeleteVo("someone", USER);
        userRoleService
                .deleteTestPlatformUseRole(vo)
                .as(StepVerifier::create)
                .verifyError(ClientTestPlatformException.class);
    }

    @Test
    void createTestPlatformUserRole() {
        var user = new TestPlatformUserRole(0L, USER, "someone");
        doReturn(Flux.just(user))
                .when(userRoleRepository)
                .findByUsername(anyString());
        var dev = new TestPlatformUserRole(1L, DEV, "someone");
        doReturn(Flux.just(dev))
                .when(userRoleRepository)
                .saveAll(ArgumentMatchers.<Publisher<TestPlatformUserRole>>any());
        var request = new UserRoleCreateRequest("someone", Set.of(USER, DEV));
        userRoleService
                .createTestPlatformUserRole(request)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void queryTestPlatformUserRole() {
        var user = new TestPlatformUserRole(0L, USER, "someone");
        var dev = new TestPlatformUserRole(1L, DEV, "someone");
        doReturn(Flux.just(user, dev))
                .when(userRoleRepository)
                .findByUsername(anyString());
        userRoleService
                .queryTestPlatformUserRole("someone")
                .as(StepVerifier::create)
                .assertNext(i -> {
                    assertEquals("USER", i.roleName());
                    assertEquals(USER, i.roleLevel());
                })
                .assertNext(i -> {
                    assertEquals("DEV", i.roleName());
                    assertEquals(DEV, i.roleLevel());
                })
                .verifyComplete();
    }

}
