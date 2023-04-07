package org.huhu.test.platform.service.impl;

import com.fasterxml.uuid.Generators;
import org.huhu.test.platform.constant.TestPlatformRole;
import org.huhu.test.platform.model.request.AddTestPlatformUserRequest;
import org.huhu.test.platform.model.table.TestPlatformUser;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.repository.TestPlatformUserRepository;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

@Service
public class TestPlatformUserServiceImpl implements TestPlatformUserService {

    private final TestPlatformUserRepository testPlatformUserRepository;

    private final TestPlatformUserRoleRepository testPlatformUserRoleRepository;

    public TestPlatformUserServiceImpl(TestPlatformUserRepository testPlatformUserRepository,
            TestPlatformUserRoleRepository testPlatformUserRoleRepository) {
        this.testPlatformUserRepository = testPlatformUserRepository;
        this.testPlatformUserRoleRepository = testPlatformUserRoleRepository;
    }

    @Override
    public Mono<TestPlatformUser> queryTestPlatformUser(String username) {
        TestPlatformUser example = new TestPlatformUser();
        example.setUsername(username);
        return testPlatformUserRepository.findOne(Example.of(example));
    }

    @Override
    public Mono<Void> saveTestPlatformUser(AddTestPlatformUserRequest request) {
        long userId = Generators.timeBasedGenerator().generate().timestamp();

        TestPlatformUser testPlatformUser = new TestPlatformUser();
        testPlatformUser.setUserId(userId);
        testPlatformUser.setUsername(request.getUsername());
        testPlatformUser.setPassword(request.getPassword());
        Mono<TestPlatformUser> saveUser = testPlatformUserRepository.save(testPlatformUser);

        Stream<TestPlatformUserRole> roleStream = request
                .getRoles()
                .stream()
                .map(role -> {
                    TestPlatformUserRole testPlatformUserRole = new TestPlatformUserRole();
                    testPlatformUserRole.setUserId(userId);
                    testPlatformUserRole.setUserRole(TestPlatformRole.getRoleName(role));
                    return testPlatformUserRole;
                });
        Flux<TestPlatformUserRole> saveUserRoles = testPlatformUserRoleRepository.saveAll(Flux.fromStream(roleStream));

        return saveUser.thenMany(saveUserRoles).then();
    }

}
