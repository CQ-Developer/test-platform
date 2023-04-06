package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.model.TestPlatformUserDetails;
import org.huhu.test.platform.model.table.TestPlatformUser;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.repository.TestPlatformUserRepository;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TestPlatformReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {

    private final TestPlatformUserRepository testPlatformUserRepository;

    private final TestPlatformUserRoleRepository testPlatformUserRoleRepository;

    private final PasswordEncoder passwordEncoder;

    public TestPlatformReactiveUserDetailsServiceImpl(PasswordEncoder passwordEncoder,
            TestPlatformUserRepository testPlatformUserRepository,
            TestPlatformUserRoleRepository testPlatformUserRoleRepository) {
        this.testPlatformUserRepository = testPlatformUserRepository;
        this.testPlatformUserRoleRepository = testPlatformUserRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        TestPlatformUser example = new TestPlatformUser();
        example.setUsername(username);
        return testPlatformUserRepository.findOne(Example.of(example)).flatMap(this::findWithRoles);
    }

    private Mono<UserDetails> findWithRoles(TestPlatformUser testPlatformUser) {
        Mono<TestPlatformUser> user = Mono.just(testPlatformUser);

        Long userId = testPlatformUser.getUserId();
        TestPlatformUserRole example = new TestPlatformUserRole();
        example.setUserId(userId);
        Mono<List<TestPlatformUserRole>> roles =
                testPlatformUserRoleRepository.findAll(Example.of(example)).collectList();

        return Mono.zip(user, roles, TestPlatformUserDetails::new);
    }

}
