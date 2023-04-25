package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.huhu.test.platform.model.request.UserRoleCreateRequest;
import org.huhu.test.platform.model.response.UserRoleQueryResponse;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.model.vo.UserRoleDeleteVo;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.huhu.test.platform.service.TestPlatformUserRoleService;
import org.huhu.test.platform.util.CollectionUtils;
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
                .findByUsername(request.username())
                .map(TestPlatformUserRole::roleLevel)
                .collectList()
                .zipWith(Mono.just(request.roleLevel()))
                .map(CollectionUtils::subtract)
                .flatMapMany(Flux::fromIterable)
                .zipWith(Mono.just(request)
                             .map(UserRoleCreateRequest::username)
                             .repeat())
                .map(ConvertUtils::toTestPlatformUserRole)
                .window(10)
                .flatMap(userRoleRepository::saveAll)
                .doOnNext(i -> logger.info("save user {} with role {}", i.username(), i.roleLevel().name()))
                .then();
    }

    @Override
    public Mono<Void> deleteTestPlatformUseRole(UserRoleDeleteVo vo) {
        var saveRole = userRoleRepository
                .deleteByUsernameAndRoleLevel(vo.username(), vo.roleLevel())
                .doOnNext(count -> logger.info("delete {} role.", count));
        return userRoleRepository
                .countByUsername(vo.username())
                .filter(i -> i <= 1)
                .flatMap(i -> Mono.error(new ClientTestPlatformException("user must hold at list 1 role")))
                .switchIfEmpty(saveRole)
                .then();
    }

}
