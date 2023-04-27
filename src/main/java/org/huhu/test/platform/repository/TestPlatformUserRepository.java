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

    /**
     * 更新密码过期时间
     *
     * @param passwordTime 过期时间
     * @param username 用户名
     */
    @Modifying
    @Query("update t_test_user set password_time = :passwordTime where username = :username")
    Mono<Integer> setPasswordTimeFor(LocalDateTime passwordTime, String username);

    /**
     * 激活用户
     *
     * @param username 用户名
     */
    @Modifying
    @Query("update t_test_user set enabled = true where username = :username")
    Mono<Integer> enableFor(String username);

    /**
     * 禁用用户
     *
     * @param username 用户名
     */
    @Modifying
    @Query("update t_test_user set enabled = false where username = :username")
    Mono<Integer> disableFor(String username);

    /**
     * 锁定用户
     *
     * @param username 用户名
     */
    @Modifying
    @Query("update t_test_user set locked = true where username = :username")
    Mono<Integer> lockFor(String username);

    /**
     * 解锁用户
     *
     * @param username 用户名
     */
    @Modifying
    @Query("update t_test_user set locked = false where username = :username")
    Mono<Integer> unlockFor(String username);

}
