package org.huhu.test.platform.repository;

import org.huhu.test.platform.model.table.TestPlatformUser;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

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

    /**
     * 更新过期时间
     *
     * @param expiredTime 过期时间
     * @param username 用户名
     */
    @Modifying
    @Query("update t_test_user set expired_time = :expiredTime where username = :username")
    Mono<Integer> setExpiredTimeFor(LocalDateTime expiredTime, String username);

}
