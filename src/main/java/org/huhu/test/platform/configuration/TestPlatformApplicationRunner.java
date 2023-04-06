package org.huhu.test.platform.configuration;

import com.fasterxml.uuid.Generators;
import org.huhu.test.platform.constant.TestPlatformRole;
import org.huhu.test.platform.model.table.TestPlatformUser;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.repository.TestPlatformUserRepository;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.huhu.test.platform.constant.TestPlatformRole.ADMIN;

@Component
public class TestPlatformApplicationRunner implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;

    private final TestPlatformUserRepository userRepository;

    private final TestPlatformUserRoleRepository userRoleRepository;

    public TestPlatformApplicationRunner(PasswordEncoder passwordEncoder,
            TestPlatformUserRepository userRepository, TestPlatformUserRoleRepository userRoleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long userId = Generators.timeBasedGenerator().generate().timestamp();

        TestPlatformUser testPlatformUser = new TestPlatformUser();
        testPlatformUser.setUserId(userId);
        testPlatformUser.setUsername("root");
        testPlatformUser.setPassWord(passwordEncoder.encode("root"));
        testPlatformUser.setEnabled(true);
        testPlatformUser.setLocked(false);
        LocalDateTime now = LocalDateTime.now();
        testPlatformUser.setExpiredTime(now);
        testPlatformUser.setExpiredTime(now.plusYears(1L));
        Mono<TestPlatformUser> saveUser = userRepository.save(testPlatformUser);

        TestPlatformUserRole testPlatformUserRole = new TestPlatformUserRole();
        testPlatformUserRole.setUserId(userId);
        testPlatformUserRole.setUserRole(TestPlatformRole.getRoleName(ADMIN));
        Mono<TestPlatformUserRole> saveUserRole = userRoleRepository.save(testPlatformUserRole);


        TestPlatformUser example = new TestPlatformUser();
        example.setUsername("root");

        userRepository.findOne(Example.of(example))
                      .switchIfEmpty(saveUser)
                      .then(saveUserRole)
                      .subscribe();

    }

}
