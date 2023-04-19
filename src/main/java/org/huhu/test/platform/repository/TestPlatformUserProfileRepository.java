package org.huhu.test.platform.repository;

import org.huhu.test.platform.model.table.TestPlatformUserProfile;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface TestPlatformUserProfileRepository extends R2dbcRepository<TestPlatformUserProfile, Long> {

    /**
     * 根据用户名查找用户环境
     *
     * @param username 用户名
     */
    Flux<TestPlatformUserProfile> findByUsername(String username);

}
