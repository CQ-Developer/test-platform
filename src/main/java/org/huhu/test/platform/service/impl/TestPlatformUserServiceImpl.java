package org.huhu.test.platform.service.impl;

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
import org.springframework.stereotype.Service;
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
                .map(TestPlatformUserRole::getUserRole)
                .collectList();
        return Mono.zip(findUser, findUserRoles, UserDetailQueryResponse::build);
    }

    @Override
    public Flux<UserQueryResponse> queryTestPlatformUsers() {
        return userRoleRepository
                .findAll()
                .groupBy(TestPlatformUserRole::getUsername)
                .flatMap(UserQueryResponse::build);
    }

    /**
     * todo 添加事务控制
     */
    @Override
    public Mono<Void> createTestPlatformUser(UserCreationRequest request) {
        String username = request.getUsername();
        logger.info("delete user {}", username);

        var user = new TestPlatformUser(username);
        user.setPassword(request.getPassword());
        request.getEnabled().ifPresent(user::setEnabled);
        request.getLocked().ifPresent(user::setLocked);
        request.getExpiredTime().ifPresent(user::setExpiredTime);
        var saveUser = userRepository.save(user);
        var saveUserRoles = Flux
                .zip(Flux.fromIterable(request.getRoles()), Flux.just(username).repeat(), TestPlatformUserRole::build)
                .collectList()
                .flatMapMany(userRoleRepository::saveAll);

        return saveUser.thenMany(saveUserRoles).then();
    }

    /**
     * todo 添加事务控制
     */
    @Override
    public Mono<Void> deleteTestPlatformUser(String username) {
        var deleteUser = userRepository.deleteByUsername(username);
        var deleteUserRole = userRoleRepository.deleteByUsername(username);
        return deleteUser.then(deleteUserRole);
    }

}
