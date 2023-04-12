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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

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

    /**
     * todo 添加事务控制
     */
    @Override
    public Mono<Void> createTestPlatformUser(UserCreateRequest request) {
        // todo 检查用户是否存在
        String username = request.username();
        logger.info("delete user {}", username);
        // 创建用户
        var user = new TestPlatformUser();
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setExpiredTime(LocalDateTime.now().plusYears(1L));
        // 保存用户
        var saveUser = userRepository.save(user);
        // 保存角色
        var saveUserRoles = Flux
                .zip(Flux.fromIterable(request.roles()),
                     Flux.just(username).repeat(),
                     TestPlatformUserRole::fromRoleNameUsername)
                .collectList()
                .flatMapMany(userRoleRepository::saveAll);
        // 执行
        return saveUser.thenMany(saveUserRoles).then();
    }

    /**
     * todo 添加事务控制
     */
    @Override
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
