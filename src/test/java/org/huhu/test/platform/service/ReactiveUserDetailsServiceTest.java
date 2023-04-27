package org.huhu.test.platform.service;

import org.huhu.test.platform.model.table.TestPlatformUser;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.repository.TestPlatformUserRepository;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.huhu.test.platform.constant.TestPlatformRoleLevel.DEV;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static reactor.test.StepVerifier.create;

@SpringBootTest
class ReactiveUserDetailsServiceTest {

    @Autowired
    ReactiveUserDetailsService reactiveUserDetailsService;

    @MockBean
    TestPlatformUserRepository userRepository;

    @MockBean
    TestPlatformUserRoleRepository userRoleRepository;

    @Test
    void findByUsername() {
        var tester = new TestPlatformUser(
                0L, "tester", "123", true, false,
                LocalDateTime.now().plusMonths(6L), LocalDateTime.now(), LocalDateTime.now().plusYears(1L));
        doReturn(Mono.just(tester))
                .when(userRepository)
                .findByUsername(anyString());
        var user = new TestPlatformUserRole(0L, USER, "tester");
        var dev = new TestPlatformUserRole(0L, DEV, "tester");
        doReturn(Flux.just(user, dev))
                .when(userRoleRepository)
                .findByUsername(anyString());
        create(reactiveUserDetailsService.findByUsername("tester"))
                .assertNext(i -> {
                    assertEquals("tester", i.getUsername());
                    assertEquals("123", i.getPassword());
                    assertIterableEquals(List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_DEV")), i.getAuthorities());
                    assertTrue(i.isEnabled());
                    assertTrue(i.isAccountNonLocked());
                    assertTrue(i.isAccountNonExpired());
                    assertTrue(i.isCredentialsNonExpired());
                })
                .verifyComplete();
    }

}
