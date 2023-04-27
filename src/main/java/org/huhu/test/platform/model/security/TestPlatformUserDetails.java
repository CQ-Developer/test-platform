package org.huhu.test.platform.model.security;

import cn.hutool.core.util.BooleanUtil;
import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import org.huhu.test.platform.model.table.TestPlatformUser;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class TestPlatformUserDetails implements UserDetails {

    private final TestPlatformUser testPlatformUser;

    private final List<TestPlatformUserRole> testPlatformUserRoles;

    public TestPlatformUserDetails(TestPlatformUser testPlatformUser, List<TestPlatformUserRole> testPlatformUserRoles) {
        this.testPlatformUser = testPlatformUser;
        this.testPlatformUserRoles = testPlatformUserRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return testPlatformUserRoles
                .stream()
                .map(TestPlatformUserRole::roleLevel)
                .map(TestPlatformRoleLevel::getRoleName)
                .distinct()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return testPlatformUser.password();
    }

    @Override
    public String getUsername() {
        return testPlatformUser.username();
    }

    @Override
    public boolean isAccountNonExpired() {
        return testPlatformUser.expiredTime().isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return BooleanUtil.negate(testPlatformUser.locked());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return testPlatformUser.passwordTime().isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isEnabled() {
        return testPlatformUser.enabled();
    }

}
