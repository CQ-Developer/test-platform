package org.huhu.test.platform.repository;

import org.huhu.test.platform.constant.TestPlatformRoleName;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TestPlatformUserRoleRepository extends R2dbcRepository<TestPlatformUserRole, Long> {

    Flux<TestPlatformUserRole> findByUsername(String username);

    Mono<Integer> deleteByUsername(String username);

    Mono<Integer> deleteByUsernameAndRoleName(String username, TestPlatformRoleName roleName);

}
