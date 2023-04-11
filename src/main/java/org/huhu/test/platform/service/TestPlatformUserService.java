package org.huhu.test.platform.service;

import org.huhu.test.platform.model.request.UserCreationRequest;
import org.huhu.test.platform.model.response.UserDetailQueryResponse;
import org.huhu.test.platform.model.response.UserQueryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TestPlatformUserService {

    /**
     * 查询测试平台所有用户
     */
    Flux<UserQueryResponse> queryTestPlatformUsers();

    /**
     * 查询测试平台用户详情
     *
     * @param username 用户名
     */
    Mono<UserDetailQueryResponse> queryTestPlatformUser(String username);

    /**
     * 创建测试平台用户
     *
     * @param request 请求体
     */
    Mono<Void> createTestPlatformUser(UserCreationRequest request);

    /**
     * 删除测试平台用户
     *
     * @param username 用户名
     */
    Mono<Void> deleteTestPlatformUser(String username);

}
