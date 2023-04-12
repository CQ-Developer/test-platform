package org.huhu.test.platform.repository;

import org.huhu.test.platform.model.table.TestPlatformUser;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface TestPlatformUserRepository extends R2dbcRepository<TestPlatformUser, Long> {

    /**
     * 查询用户
     *
     * @param username 用户名
     */
    Mono<TestPlatformUser> findByUsername(String username);

    /**
     * 删除用户
     *
     * @param username 用户名
     */
    Mono<Integer> deleteByUsername(String username);

}
