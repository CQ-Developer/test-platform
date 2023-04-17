package org.huhu.test.platform.model.security;

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
                .map(TestPlatformRoleLevel::name)
                .distinct()
                .map("ROLE_"::concat)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return testPlatformUser.getPassword();
    }

    @Override
    public String getUsername() {
        return testPlatformUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return testPlatformUser.getExpiredTime().isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !testPlatformUser.getLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return testPlatformUser.getExpiredTime().isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isEnabled() {
        return testPlatformUser.getEnabled();
    }

}
