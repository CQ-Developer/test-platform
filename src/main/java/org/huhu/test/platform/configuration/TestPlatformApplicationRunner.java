package org.huhu.test.platform.configuration;

import org.huhu.test.platform.constant.TestPlatformRole;
import org.huhu.test.platform.model.table.TestPlatformUser;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.repository.TestPlatformUserRepository;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.huhu.test.platform.constant.TestPlatformRole.ADMIN;

/**
 * 创建一个默认的root用户
 */
@Component
public class TestPlatformApplicationRunner implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;

    private final TestPlatformUserRepository userRepository;

    private final TestPlatformUserRoleRepository userRoleRepository;

    private final Logger logger = LoggerFactory.getLogger(TestPlatformApplicationRunner.class);

    public TestPlatformApplicationRunner(PasswordEncoder passwordEncoder,
            TestPlatformUserRepository userRepository, TestPlatformUserRoleRepository userRoleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        TestPlatformUser testPlatformUser = new TestPlatformUser();
        testPlatformUser.setUsername("root");
        testPlatformUser.setPassword(passwordEncoder.encode("root"));
        testPlatformUser.setEnabled(true);
        testPlatformUser.setLocked(false);
        LocalDateTime now = LocalDateTime.now();
        testPlatformUser.setExpiredTime(now);
        testPlatformUser.setExpiredTime(now.plusYears(1L));
        Mono<TestPlatformUser> saveUser = userRepository
                .save(testPlatformUser)
                .doOnNext(user -> logger.info("create root user with password root"));

        TestPlatformUserRole testPlatformUserRole = new TestPlatformUserRole();
        testPlatformUserRole.setUsername(testPlatformUser.getUsername());
        testPlatformUserRole.setUserRole(TestPlatformRole.getRoleName(ADMIN));
        Mono<TestPlatformUserRole> saveUserRole = userRoleRepository
                .save(testPlatformUserRole)
                .doOnNext(userRole -> logger.info("create root user with role ADMIN"));

        TestPlatformUser example = new TestPlatformUser();
        example.setUsername("root");
        userRepository
                .exists(Example.of(example))
                .flatMap(exist -> {
                    if (exist) {
                        logger.info("root user exists.");
                        return Mono.empty();
                    }
                    return saveUser.then(saveUserRole);
                })
                .subscribe();
    }

}
