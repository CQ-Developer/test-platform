package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.constant.TestPlatformRole;
import org.huhu.test.platform.model.request.UserCreationRequest;
import org.huhu.test.platform.model.response.UserDetailQueryResponse;
import org.huhu.test.platform.model.response.UserQueryResponse;
import org.huhu.test.platform.model.table.TestPlatformUser;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.repository.TestPlatformUserRepository;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

@Service
public class TestPlatformUserServiceImpl implements TestPlatformUserService {

    private final Logger logger = LoggerFactory.getLogger(TestPlatformUserServiceImpl.class);

    private final TestPlatformUserRepository userRepository;

    private final TestPlatformUserRoleRepository userRoleRepository;

    public TestPlatformUserServiceImpl(TestPlatformUserRepository userRepository,
            TestPlatformUserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Mono<UserDetailQueryResponse> queryTestPlatformUser(String username) {
        return userRepository
                .findOne(Example.of(new TestPlatformUser(username)))
                .flatMap(user -> userRoleRepository
                        .findAll(Example.of(new TestPlatformUserRole(user.getUsername())))
                        .map(TestPlatformUserRole::getUserRole)
                        .collectList()
                        .map(userRoles -> {
                            UserDetailQueryResponse response = new UserDetailQueryResponse();
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
    public Flux<UserQueryResponse> queryTestPlatformUsers() {
        return userRepository
                .findAll()
                .flatMap(user -> userRoleRepository
                        .findAll(Example.of(new TestPlatformUserRole(user.getUsername())))
                        .map(TestPlatformUserRole::getUserRole)
                        .collectList()
                        .map(userRoles -> {
                            UserQueryResponse response = new UserQueryResponse();
                            response.setName(user.getUsername());
                            response.setRoles(userRoles);
                            return response;
                        }));
    }

    @Override
    public Mono<Void> createTestPlatformUser(UserCreationRequest request) {
        TestPlatformUser testPlatformUser = new TestPlatformUser(request.getUsername());
        testPlatformUser.setPassword(request.getPassword());
        request.getEnabled().ifPresent(testPlatformUser::setEnabled);
        request.getLocked().ifPresent(testPlatformUser::setLocked);
        request.getExpiredTime().ifPresent(testPlatformUser::setExpiredTime);
        Mono<TestPlatformUser> saveUser = userRepository
                .save(testPlatformUser)
                .doOnNext(user -> logger.info("save test platform user {}", user.getUsername()));

        Stream<TestPlatformUserRole> roleStream =
                request.getRoles()
                       .stream()
                       .map(role -> {
                           TestPlatformUserRole testPlatformUserRole = new TestPlatformUserRole(request.getUsername());
                           testPlatformUserRole.setUserRole(TestPlatformRole.getRoleName(role));
                           return testPlatformUserRole;
                       });
        Flux<TestPlatformUserRole> saveUserRoles = userRoleRepository
                .saveAll(Flux.fromStream(roleStream))
                .doOnNext(userRole -> logger.info("save test platform user role {}", userRole.getUserRole()));

        return saveUser.thenMany(saveUserRoles).then();
    }

    @Override
    public Mono<Void> deleteTestPlatformUser(String username) {
        return userRepository
                .findOne(Example.of(new TestPlatformUser(username)))
                .map(TestPlatformUser::getUserId)
                .doOnNext(userId -> logger.info("delete test platform user {}", userId))
                .flatMap(userRepository::deleteById)
                .thenMany(userRoleRepository.findAll(Example.of(new TestPlatformUserRole(username))))
                .map(TestPlatformUserRole::getRoleId)
                .doOnNext(roleId -> logger.info("delete test platform user role {}", roleId))
                .collectList()
                .flatMap(userRoleRepository::deleteAllById);
    }

}
