package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.model.request.UserRoleCreateRequest;
import org.huhu.test.platform.model.response.UserRoleQueryResponse;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.model.vo.UserRoleDeleteVo;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.huhu.test.platform.service.TestPlatformUserRoleService;
import org.huhu.test.platform.util.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TestPlatformUserRoleServiceImpl implements TestPlatformUserRoleService {

    private final Logger logger = LoggerFactory.getLogger(TestPlatformUserRoleServiceImpl.class);

    private final TestPlatformUserRoleRepository userRoleRepository;

    TestPlatformUserRoleServiceImpl(TestPlatformUserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Flux<UserRoleQueryResponse> queryTestPlatformUserRole(String username) {
        return userRoleRepository
                .findByUsername(username)
                .map(TestPlatformUserRole::roleLevel)
                .map(ConvertUtils::toUserRoleQueryResponse);
    }

    @Override
    public Mono<Void> createTestPlatformUserRole(UserRoleCreateRequest request) {
        return userRoleRepository
                .findByUsernameAndRoleLevel(request.username(), request.roleLevel())
                .switchIfEmpty(userRoleRepository.save(ConvertUtils.toTestPlatformUserRole(request)))
                .doOnNext(item -> logger.info("save user role {}", item.roleLevel()))
                .then();
    }

    @Override
    public Mono<Void> deleteTestPlatformUseRole(UserRoleDeleteVo vo) {
        return userRoleRepository
                .deleteByUsernameAndRoleLevel(vo.username(), vo.roleLevel())
                .doOnNext(count -> logger.info("delete {} role.", count))
                .then();
    }

}
