package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.model.request.UserCreateRequest;
import org.huhu.test.platform.model.response.UserDetailQueryResponse;
import org.huhu.test.platform.model.response.UserQueryResponse;
import org.huhu.test.platform.model.table.TestPlatformUser;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.repository.TestPlatformUserRepository;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        var findUser = userRepository
                .findByUsername(username);
        var findUserRoles = userRoleRepository
                .findByUsername(username)
                .map(TestPlatformUserRole::getRoleName)
                .collectList();
        return Mono.zip(findUser, findUserRoles, UserDetailQueryResponse::from);
    }

    @Override
    public Flux<UserQueryResponse> queryTestPlatformUsers() {
        return userRoleRepository
                .findAll()
                .groupBy(TestPlatformUserRole::getUsername)
                .flatMap(UserQueryResponse::from);
    }

    @Override
    @Transactional
    public Mono<Void> createTestPlatformUser(UserCreateRequest request) {
        var findUser = userRepository
                .findByUsername(request.username());
        var saveUser = userRepository
                .save(TestPlatformUser.from(request))
                .doOnNext(i -> logger.info("create user {}", i.getUsername()));
        var saveRole = userRoleRepository
                .saveAll(TestPlatformUserRole.from(request))
                .doOnNext(i -> logger.info("create user {} with role {}", i.getUsername(), i.getRoleName()));
        return findUser.flatMap(i -> saveUser)
                       .flatMapMany(i -> saveRole)
                       .switchIfEmpty(Mono.empty())
                       .then();
    }

    @Override
    @Transactional
    public Mono<Void> deleteTestPlatformUser(String username) {
        var deleteUser = userRepository
                .deleteByUsername(username)
                .doOnNext(count -> logger.info("delete {} user", count));
        var deleteUserRole = userRoleRepository
                .deleteByUsername(username)
                .doOnNext(count -> logger.info("delete {} role", count));
        return deleteUser.then(deleteUserRole).then();
    }

}
