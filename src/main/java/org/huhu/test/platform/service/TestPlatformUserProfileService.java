package org.huhu.test.platform.service;

import org.huhu.test.platform.model.response.UserProfileQueryResponse;
import reactor.core.publisher.Mono;

/**
 * 测试平台用户环境 {@link org.springframework.stereotype.Service} 接口
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public interface TestPlatformUserProfileService {

    /**
     * 查询测试平台用户环境
     *
     * @param username 用户名
     */
    Mono<UserProfileQueryResponse> queryTestPlatformUserProfile(String username);

}
