package org.huhu.test.platform.service;

import org.huhu.test.platform.model.request.UserCreateRequest;
import org.huhu.test.platform.model.request.UserModifyRequest;
import org.huhu.test.platform.model.response.UserDetailQueryResponse;
import org.huhu.test.platform.model.response.UserQueryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 测试平台用户 {@link org.springframework.stereotype.Service} 接口
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public interface TestPlatformUserService {

    /**
     * 查询测试平台所有用户
     */
    Flux<UserQueryResponse> queryTestPlatformUser();

    /**
     * 查询测试平台用户详情
     *
     * @param username 用户名
     */
    Mono<UserDetailQueryResponse> queryTestPlatformUserDetail(String username);

    /**
     * 创建测试平台用户
     *
     * @param request 请求体
     */
    Mono<Void> createTestPlatformUser(UserCreateRequest request);

    /**
     * 删除测试平台用户
     *
     * @param username 用户名
     */
    Mono<Void> deleteTestPlatformUser(String username);

    /**
     * 刷新测试平台用户过期时间
     *
     * @param request 请求体
     */
    Mono<Void> renewTestPlatformUser(UserModifyRequest request);

    /**
     * 刷新测试平台用户密码过期时间
     *
     * @param request 请求体
     */
    Mono<Void> verifyTestPlatformUser(UserModifyRequest request);

    /**
     * 启用测试平台用户
     *
     * @param username 用户名
     */
    Mono<Void> enableTestPlatformUser(String username);

    /**
     * 禁用测试平台用户
     *
     * @param username 用户名
     */
    Mono<Void> disableTestPlatformUser(String username);

    /**
     * 锁定测试平台用户
     *
     * @param username 用户名
     */
    Mono<Void> lockTestPlatformUser(String username);

    /**
     * 解锁测试平台用户
     *
     * @param username 用户名
     */
    Mono<Void> unlockTestPlatformUser(String username);

}
