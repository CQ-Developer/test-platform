package org.huhu.test.platform.model;

import org.huhu.test.platform.model.table.TestPlatformUser;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
                .map(TestPlatformUserRole::getUserRole)
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
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
