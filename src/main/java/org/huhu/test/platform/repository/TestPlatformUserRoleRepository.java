package org.huhu.test.platform.repository;

import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TestPlatformUserRoleRepository extends R2dbcRepository<TestPlatformUserRole, Long> {

    /**
     * 查询用户角色
     *
     * @param username 用户名
     */
    Flux<TestPlatformUserRole> findByUsername(String username);

    /**
     * 删除用户所有角色
     *
     * @param username 用户名
     */
    Mono<Integer> deleteByUsername(String username);

    /**
     * 删除用户角色
     *
     * @param username 用户名
     * @param roleLevel 角色級別
     */
    Mono<Integer> deleteByUsernameAndRoleLevel(String username, TestPlatformRoleLevel roleLevel);

}
