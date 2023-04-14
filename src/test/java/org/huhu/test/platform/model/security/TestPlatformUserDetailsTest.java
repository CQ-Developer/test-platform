package org.huhu.test.platform.model.security;

import org.huhu.test.platform.model.table.TestPlatformUser;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.huhu.test.platform.constant.TestPlatformRoleName.ADMIN;
import static org.huhu.test.platform.constant.TestPlatformRoleName.DEV;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 测试平台 {@link org.springframework.security.core.userdetails.UserDetails} 单元测试
 *
 * @author 18551681083@163.com
 * @see TestPlatformUserDetails
 * @since 0.0.1
 */
class TestPlatformUserDetailsTest {

    @Test
    void testPlatformUserDetails() {
        var user = new TestPlatformUser();
        user.setUsername("jack");
        user.setPassword("jack2023");
        user.setEnabled(true);
        user.setLocked(false);
        user.setRegisterTime(LocalDateTime.now());
        user.setExpiredTime(LocalDateTime.now().plusYears(1L));

        TestPlatformUserRole admin = new TestPlatformUserRole(ADMIN, "jack");
        TestPlatformUserRole dev = new TestPlatformUserRole(DEV, "jack");
        var userRoles = List.of(dev, admin);

        var userDetails = new TestPlatformUserDetails(user, userRoles);
        assertEquals("jack", userDetails.getUsername());
        assertEquals("jack2023", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertIterableEquals(Set.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("ROLE_DEV")),
                userDetails.getAuthorities());
    }

}
