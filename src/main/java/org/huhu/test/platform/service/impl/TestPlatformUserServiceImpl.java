package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.huhu.test.platform.model.request.UserCreateRequest;
import org.huhu.test.platform.model.request.UserRenewRequest;
import org.huhu.test.platform.model.response.UserDetailQueryResponse;
import org.huhu.test.platform.model.response.UserQueryResponse;
import org.huhu.test.platform.model.table.TestPlatformUser;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.repository.TestPlatformUserRepository;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
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

    private final String userTableName = "t_test_user";

    private final PasswordEncoder passwordEncoder;

    private final TestPlatformUserRepository userRepository;

    private final TestPlatformUserRoleRepository userRoleRepository;

    TestPlatformUserServiceImpl(PasswordEncoder passwordEncoder,
            TestPlatformUserRepository userRepository, TestPlatformUserRoleRepository userRoleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Mono<UserDetailQueryResponse> queryTestPlatformUser(String username) {
        var findUser = userRepository
                .findByUsername(username)
                .switchIfEmpty(Mono.error(new ClientTestPlatformException()));
        var findUserRoles = userRoleRepository
                .findByUsername(username)
                .map(TestPlatformUserRole::roleLevel)
                .collectList();
        return Mono.zip(findUser, findUserRoles, ConvertUtils::toUserDetailQueryResponse);
    }

    @Override
    public Flux<UserQueryResponse> queryTestPlatformUser() {
        return userRoleRepository
                .findAll()
                .groupBy(TestPlatformUserRole::username)
                .flatMap(ConvertUtils::toUserQueryResponse);
    }

    @Override
    @Transactional
    public Mono<Void> createTestPlatformUser(UserCreateRequest request) {
        var testPlatformUser = ConvertUtils
                .toTestPlatformUser(request, passwordEncoder.encode(request.password()));
        var saveUser = userRepository
                .save(testPlatformUser)
                .doOnNext(i -> logger.info("create user {}", i.username()));
        var saveRole = userRoleRepository
                .saveAll(ConvertUtils.toTestPlatformUserRole(request))
                .doOnNext(i -> logger.info("create user {} with role {}", i.username(), i.roleLevel()));
        return userRepository
                .findByUsername(request.username())
                .switchIfEmpty(saveUser)
                .doOnNext(i -> logger.info("save user {}", i.username()))
                .thenMany(saveRole)
                .doOnNext(i -> logger.info("save user role {}", i.roleLevel().name()))
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
        // todo 删除用户的变量
        return deleteUser.then(deleteUserRole).then();
    }

    @Override
    public Mono<Void> renewTestPlatformUser(UserRenewRequest request) {
        return userRepository
                .findByUsername(request.username())
                .map(TestPlatformUser::expiredTime)
                .doOnNext(i -> logger.info("user current expired time {}", i))
                .filter(LocalDateTime.now()::isBefore)
                .flatMap(i -> Mono
                        .justOrEmpty(request.expiredTime())
                        .filter(i::isBefore)
                        .switchIfEmpty(Mono.just(i)))
                .switchIfEmpty(Mono
                        .justOrEmpty(request.expiredTime())
                        .filter(LocalDateTime.now()::isBefore)
                        .switchIfEmpty(Mono.just(LocalDateTime.now().plusMonths(1L)))
                        .doOnNext(i -> logger.info("will renew user with {}", i)))
                .zipWith(Mono.just(request.username()))
                .flatMap(i -> userRepository.setExpiredTimeFor(i.getT1(), i.getT2()))
                .doOnNext(i -> logger.info("renew {} user", i))
                .then();
    }

    @Override
    public Mono<Void> enableTestPlatformUser(String username) {
        return userRepository
                .enableFor(username)
                .doOnNext(i -> logger.info("enable {} user", i))
                .then();
    }

    @Override
    public Mono<Void> disableTestPlatformUser(String username) {
        return userRepository
                .disableFor(username)
                .doOnNext(i -> logger.info("disable {} user", i))
                .then();
    }

    @Override
    public Mono<Void> lockTestPlatformUser(String username) {
        return userRepository
                .lockFor(username)
                .doOnNext(i -> logger.info("lock {} user", i))
                .then();
    }

    @Override
    public Mono<Void> unlockTestPlatformUser(String username) {
        return userRepository
                .unlockFor(username)
                .doOnNext(i -> logger.info("unlock {} user", i))
                .then();
    }

}
