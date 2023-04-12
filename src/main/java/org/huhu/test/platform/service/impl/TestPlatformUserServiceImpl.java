package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.exception.UsernameInvalidException;
import org.huhu.test.platform.model.request.UserCreateRequest;
import org.huhu.test.platform.model.request.UserRenewRequest;
import org.huhu.test.platform.model.response.UserDetailQueryResponse;
import org.huhu.test.platform.model.response.UserQueryResponse;
import org.huhu.test.platform.model.table.TestPlatformUser;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.repository.TestPlatformUserRepository;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TestPlatformUserServiceImpl implements TestPlatformUserService {

    private final Logger logger = LoggerFactory.getLogger(TestPlatformUserServiceImpl.class);

    private final String userTableName = "t_test_user";

    private final PasswordEncoder passwordEncoder;

    private final R2dbcEntityTemplate entityTemplate;

    private final TestPlatformUserRepository userRepository;

    private final TestPlatformUserRoleRepository userRoleRepository;

    TestPlatformUserServiceImpl(PasswordEncoder passwordEncoder, R2dbcEntityTemplate entityTemplate,
            TestPlatformUserRepository userRepository, TestPlatformUserRoleRepository userRoleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.entityTemplate = entityTemplate;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Mono<UserDetailQueryResponse> queryTestPlatformUser(String username) {
        var findUser = userRepository
                .findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameInvalidException()));
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
        var testPlatformUser = TestPlatformUser.from(request);
        testPlatformUser.setPassword(passwordEncoder.encode(request.password()));
        var saveUser = userRepository
                .save(testPlatformUser)
                .doOnNext(i -> logger.info("create user {}", i.getUsername()));
        var saveRole = userRoleRepository
                .saveAll(TestPlatformUserRole.from(request))
                .doOnNext(i -> logger.info("create user {} with role {}", i.getUsername(), i.getRoleName()));
        return userRepository
                .findByUsername(request.username())
                .flatMap(i -> Mono.error(new UsernameInvalidException()))
                .switchIfEmpty(saveUser)
                .thenMany(saveRole)
                .then();
    }

    @Override
    @Transactional
    public Mono<Void> deleteTestPlatformUser(String username) {
        var deleteUser = userRepository
                .deleteByUsername(username)
                .doOnNext(i -> logger.info("delete {} user", i));
        var deleteUserRole = userRoleRepository
                .deleteByUsername(username)
                .doOnNext(i -> logger.info("delete {} role", i));
        return deleteUser.then(deleteUserRole).then();
    }

    @Override
    public Mono<Void> renewTestPlatformUser(UserRenewRequest request) {
        var expiredTime = Optional
                .ofNullable(request.expiredTime())
                .orElse(LocalDateTime.now().plusYears(1L));
        return entityTemplate
                .update(TestPlatformUser.class)
                .inTable(userTableName)
                .matching(Query.query(Criteria.where("username").is(request.username())))
                .apply(Update.update("expired_time", expiredTime))
                .doOnNext(i -> logger.info("renew {} user", i))
                .then();
    }

    @Override
    public Mono<Void> enableTestPlatformUser(String username) {
        return enableOrDisableUser(username, true)
                .doOnNext(i -> logger.info("enable {} user", i))
                .then();
    }

    @Override
    public Mono<Void> disableTestPlatformUser(String username) {
        return enableOrDisableUser(username, false)
                .doOnNext(i -> logger.info("disable {} user", i))
                .then();
    }

    @Override
    public Mono<Void> lockTestPlatformUser(String username) {
        return lockOrUnlockUser(username, true)
                .doOnNext(i -> logger.info("lock {} user", i))
                .then();
    }

    @Override
    public Mono<Void> unlockTestPlatformUser(String username) {
        return lockOrUnlockUser(username, false)
                .doOnNext(i -> logger.info("unlock {} user", i))
                .then();
    }

    /**
     * 启用或禁用用户
     *
     * @param username 用户名
     * @param isEnable 是否启用
     */
    private Mono<Long> enableOrDisableUser(String username, boolean isEnable) {
        return entityTemplate
                .update(TestPlatformUser.class)
                .inTable(userTableName)
                .matching(Query.query(Criteria.where("username").is(username)))
                .apply(Update.update("enabled", isEnable));
    }

    /**
     * 锁定或解锁用户
     *
     * @param username 用户名
     * @param isLocked 是否启用
     */
    private Mono<Long> lockOrUnlockUser(String username, boolean isLocked) {
        return entityTemplate
                .update(TestPlatformUser.class)
                .inTable(userTableName)
                .matching(Query.query(Criteria.where("username").is(username)))
                .apply(Update.update("locked", isLocked));
    }

}
