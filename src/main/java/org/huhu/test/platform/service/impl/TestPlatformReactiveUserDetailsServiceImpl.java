package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.model.security.TestPlatformUserDetails;
import org.huhu.test.platform.repository.TestPlatformUserRepository;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * spring security {@link ReactiveUserDetailsService} 实现.
 *
 * @see UserDetails
 * @see ReactiveUserDetailsService
 * @see ReactiveUserDetailsPasswordService
 */
@Service
public class TestPlatformReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {

    private final TestPlatformUserRepository userRepository;

    private final TestPlatformUserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    public TestPlatformReactiveUserDetailsServiceImpl(PasswordEncoder passwordEncoder,
            TestPlatformUserRepository userRepository,
            TestPlatformUserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        var findUser = userRepository
                .findByUsername(username);
        var findRole = userRoleRepository
                .findByUsername(username)
                .collectList();
        return Mono.zip(findUser, findRole, TestPlatformUserDetails::new);
    }

    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        return null;
    }

}
