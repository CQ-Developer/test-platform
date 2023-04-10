package org.huhu.test.platform.service;

import org.huhu.test.platform.model.request.AddTestPlatformUserRequest;
import org.huhu.test.platform.model.response.QueryTestPlatformUserResponse;
import org.huhu.test.platform.model.response.QueryTestPlatformUsersResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TestPlatformUserService {

    /**
     * 查询测试平台所有用户
     */
    Flux<QueryTestPlatformUsersResponse> queryTestPlatformUsers();

    /**
     * 查询测试平台用户详情
     *
     * @param username 用户名
     */
    Mono<QueryTestPlatformUserResponse> queryTestPlatformUser(String username);

    /**
     * 创建测试平台用户
     *
     * @param request 请求体
     */
    Mono<Void> createTestPlatformUser(AddTestPlatformUserRequest request);

}
