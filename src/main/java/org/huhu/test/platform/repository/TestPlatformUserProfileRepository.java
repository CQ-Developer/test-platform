package org.huhu.test.platform.repository;

import org.huhu.test.platform.model.table.TestPlatformUserProfile;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TestPlatformUserProfileRepository extends R2dbcRepository<TestPlatformUserProfile, Long> {

    /**
     * 查找用户环境
     *
     * @param username 用户名
     */
    Flux<TestPlatformUserProfile> findByUsername(String username);

    /**
     * 查询用户环境
     *
     * @param username 用户名
     * @param profileName 环境名
     */
    Mono<TestPlatformUserProfile> findByUsernameAndProfileName(String username, String profileName);

    /**
     * 删除用户环境
     *
     * @param username 用户名
     */
    Mono<Integer> deleteByUsername(String username);

    /**
     * 删除用户环境
     *
     * @param username 用户名
     * @param profileName 环境名
     */
    Mono<Integer> deleteByUsernameAndProfileName(String username, String profileName);

}
