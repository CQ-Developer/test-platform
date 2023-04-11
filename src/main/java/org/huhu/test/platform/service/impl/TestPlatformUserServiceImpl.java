package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.constant.TestPlatformRole;
import org.huhu.test.platform.model.request.AddTestPlatformUserRequest;
import org.huhu.test.platform.model.response.QueryTestPlatformUserResponse;
import org.huhu.test.platform.model.response.QueryTestPlatformUsersResponse;
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

    private final TestPlatformUserRepository userRepository;

    private final TestPlatformUserRoleRepository userRoleRepository;

    public TestPlatformUserServiceImpl(TestPlatformUserRepository userRepository,
            TestPlatformUserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Mono<QueryTestPlatformUserResponse> queryTestPlatformUser(String username) {
        return userRepository
                .findOne(Example.of(new TestPlatformUser(username)))
                .flatMap(user -> userRoleRepository
                        .findAll(Example.of(new TestPlatformUserRole(user.getUsername())))
                        .map(TestPlatformUserRole::getUserRole)
                        .collectList()
                        .map(userRoles -> {
                            QueryTestPlatformUserResponse response = new QueryTestPlatformUserResponse();
                            response.setUsername(user.getUsername());
                            response.setUserRoles(userRoles);
                            response.setEnabled(user.getEnabled());
                            response.setLocked(user.getLocked());
                            response.setRegisterTime(user.getRegisterTime());
                            response.setExpiredTime(user.getExpiredTime());
                            return response;
                        }));
    }

    @Override
    public Flux<QueryTestPlatformUsersResponse> queryTestPlatformUsers() {
        return userRepository
                .findAll()
                .flatMap(user -> userRoleRepository
                        .findAll(Example.of(new TestPlatformUserRole(user.getUsername())))
                        .map(TestPlatformUserRole::getUserRole)
                        .collectList()
                        .map(userRoles -> {
                            QueryTestPlatformUsersResponse response = new QueryTestPlatformUsersResponse();
                            response.setName(user.getUsername());
                            response.setRoles(userRoles);
                            return response;
                        }));
    }

    @Override
    public Mono<Void> createTestPlatformUser(AddTestPlatformUserRequest request) {
        TestPlatformUser testPlatformUser = new TestPlatformUser(request.getUsername());
        testPlatformUser.setPassword(request.getPassword());
        request.getEnabled().ifPresent(testPlatformUser::setEnabled);
        request.getLocked().ifPresent(testPlatformUser::setLocked);
        request.getExpiredTime().ifPresent(testPlatformUser::setExpiredTime);
        Mono<TestPlatformUser> saveUser = userRepository.save(testPlatformUser);

        Stream<TestPlatformUserRole> roleStream =
                request.getRoles()
                       .stream()
                       .map(role -> {
                           TestPlatformUserRole testPlatformUserRole = new TestPlatformUserRole(request.getUsername());
                           testPlatformUserRole.setUserRole(TestPlatformRole.getRoleName(role));
                           return testPlatformUserRole;
                       });
        Flux<TestPlatformUserRole> saveUserRoles = userRoleRepository.saveAll(Flux.fromStream(roleStream));

        return saveUser.thenMany(saveUserRoles).then();
    }

}
