package org.huhu.test.platform.service.impl;

import cn.hutool.core.util.ObjectUtil;
import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.huhu.test.platform.model.request.UserCreateRequest;
import org.huhu.test.platform.model.request.UserModifyRequest;
import org.huhu.test.platform.model.response.UserDetailQueryResponse;
import org.huhu.test.platform.model.response.UserQueryResponse;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.repository.TestPlatformUserProfileRepository;
import org.huhu.test.platform.repository.TestPlatformUserRepository;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.huhu.test.platform.repository.TestPlatformVariableRepository;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.huhu.test.platform.util.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class TestPlatformUserServiceImpl implements TestPlatformUserService {

    private final Logger logger = LoggerFactory.getLogger(TestPlatformUserServiceImpl.class);

    private final PasswordEncoder passwordEncoder;

    private final TestPlatformUserRepository userRepository;

    private final TestPlatformUserRoleRepository userRoleRepository;

    private final TestPlatformUserProfileRepository userProfileRepository;

    private final TestPlatformVariableRepository variableRepository;

    TestPlatformUserServiceImpl(PasswordEncoder passwordEncoder,
            TestPlatformUserRepository userRepository,
            TestPlatformUserRoleRepository userRoleRepository,
            TestPlatformUserProfileRepository userProfileRepository,
            TestPlatformVariableRepository variableRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userProfileRepository = userProfileRepository;
        this.variableRepository = variableRepository;
    }

    @Override
    public Flux<UserQueryResponse> queryTestPlatformUser() {
        return userRoleRepository
                .findAll()
                .groupBy(TestPlatformUserRole::username)
                .flatMap(ConvertUtils::toUserQueryResponse);
    }

    @Override
    public Mono<UserDetailQueryResponse> queryTestPlatformUserDetail(String username) {
        var findUser = userRepository
                .findByUsername(username)
                .switchIfEmpty(Mono.error(new ClientTestPlatformException()));
        var findUserRoles = userRoleRepository
                .findByUsername(username)
                .map(TestPlatformUserRole::roleLevel)
                .collectList();
        return Mono.zip(findUser, findUserRoles)
                   .map(ConvertUtils::toUserDetailQueryResponse);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> createTestPlatformUser(UserCreateRequest request) {
        var saveUser = Mono
                .just(request)
                .map(UserCreateRequest::password)
                .map(passwordEncoder::encode)
                .zipWith(Mono.just(request))
                .map(ConvertUtils::toTestPlatformUser)
                .flatMap(userRepository::save)
                .doOnNext(i -> logger.info("save user {}", i.username()));
        var saveRole = Mono
                .just(request)
                .flatMapMany(ConvertUtils::toTestPlatformUserRole)
                .collectList()
                .flatMapMany(userRoleRepository::saveAll)
                .doOnNext(i -> logger.info("save role {}", i.roleLevel().name()));
        var saveUserProfile = Mono
                .just(request)
                .map(UserCreateRequest::username)
                .map(ConvertUtils::toTestPlatformUserProfile)
                .flatMap(userProfileRepository::save)
                .doOnNext(i -> logger.info("save profile {}", i.profileName()));
        var saveAll = userRepository
                .findByUsername(request.username())
                .flatMap(i -> Mono.error(new ClientTestPlatformException("save user fail: user exists")))
                .switchIfEmpty(saveUser)
                .thenMany(saveRole)
                .then(saveUserProfile);
        return Mono.when(saveAll);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> deleteTestPlatformUser(String username) {
        var deleteUser = userRepository
                .deleteByUsername(username)
                .doOnNext(i -> logger.info("delete {} user", i))
                .then();
        var deleteUserRole = userRoleRepository
                .deleteByUsername(username)
                .doOnNext(i -> logger.info("delete {} user role", i))
                .then();
        var deleteUserProfile = userProfileRepository
                .deleteByUsername(username)
                .doOnNext(i -> logger.info("delete {} user profile", i))
                .then();
        var deleteVariable = variableRepository
                .deleteByUsername(username)
                .doOnNext(i -> logger.info("delete {} variable", i))
                .then();
        return Mono.when(deleteUser, deleteUserRole, deleteUserProfile, deleteVariable);
    }

    @Override
    public Mono<Void> renewTestPlatformUser(UserModifyRequest request) {
        var newTime = request.newTime();
        newTime = ObjectUtil.isNull(newTime) ? LocalDateTime.now().plusYears(1L) : newTime;
        var renew = userRepository
                .setExpiredTimeFor(newTime, request.username())
                .doOnNext(i -> logger.info("renew {} user", i));
        return Mono.when(renew);
    }

    @Override
    public Mono<Void> verifyTestPlatformUser(UserModifyRequest request) {
        var newTime = request.newTime();
        newTime = ObjectUtil.isNull(newTime) ? LocalDateTime.now().plusMonths(6L) : newTime;
        var verify = userRepository
                .setPasswordTimeFor(newTime, request.username())
                .doOnNext(i -> logger.info("verify {} user", i));
        return Mono.when(verify);
    }

    @Override
    public Mono<Void> enableTestPlatformUser(String username) {
        var enable = userRepository
                .enableFor(username)
                .doOnNext(i -> logger.info("enable {} user", i));
        return Mono.when(enable);
    }

    @Override
    public Mono<Void> disableTestPlatformUser(String username) {
        var disable = userRepository
                .disableFor(username)
                .doOnNext(i -> logger.info("disable {} user", i));
        return Mono.when(disable);
    }

    @Override
    public Mono<Void> lockTestPlatformUser(String username) {
        var lock = userRepository
                .lockFor(username)
                .doOnNext(i -> logger.info("lock {} user", i));
        return Mono.when(lock);
    }

    @Override
    public Mono<Void> unlockTestPlatformUser(String username) {
        var unlock = userRepository
                .unlockFor(username)
                .doOnNext(i -> logger.info("unlock {} user", i));
        return Mono.when(unlock);
    }

}
