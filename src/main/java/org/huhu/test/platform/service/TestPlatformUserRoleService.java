package org.huhu.test.platform.service;

import org.huhu.test.platform.constant.TestPlatformRoleName;
import org.huhu.test.platform.model.request.UserRoleModifyRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TestPlatformUserRoleService {

    /**
     * 删除测试平台用户角色
     *
     * @param request 请求
     */
    Mono<Void> deleteTestPlatformUseRole(UserRoleModifyRequest request);

    /**
     * 创建测试平台用户角色
     *
     * @param request 请求
     */
    Mono<Void> createTestPlatformUserRole(UserRoleModifyRequest request);

    /**
     * 查询测试平台用户角色
     *
     * @param username 用户名
     */
    Flux<TestPlatformRoleName> queryTestPlatformUserRole(String username);
}
