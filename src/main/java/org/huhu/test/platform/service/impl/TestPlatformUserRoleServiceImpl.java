package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.constant.TestPlatformRoleName;
import org.huhu.test.platform.model.request.UserRoleModifyRequest;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.huhu.test.platform.service.TestPlatformUserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TestPlatformUserRoleServiceImpl implements TestPlatformUserRoleService {

    private final Logger logger = LoggerFactory.getLogger(TestPlatformUserRoleServiceImpl.class);

    private final TestPlatformUserRoleRepository userRoleRepository;

    public TestPlatformUserRoleServiceImpl(TestPlatformUserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Flux<TestPlatformRoleName> queryTestPlatformUserRole(String username) {
        return userRoleRepository
                .findByUsername(username)
                .map(TestPlatformUserRole::getRoleName);
    }

    @Override
    public Mono<Void> createTestPlatformUserRole(UserRoleModifyRequest request) {
        return userRoleRepository
                .save(TestPlatformUserRole.fromRequest(request))
                .doOnNext(item -> logger.info("save user role {}", item.getRoleName()))
                .then();
    }

    @Override
    public Mono<Void> deleteTestPlatformUseRole(UserRoleModifyRequest request) {
        return userRoleRepository
                .deleteByUsernameAndRoleName(request.username(), request.roleName())
                .doOnNext(count -> logger.info("delete {} role.", count))
                .then();
    }

}
