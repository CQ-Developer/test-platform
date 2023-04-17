package org.huhu.test.platform.service;

import org.huhu.test.platform.model.request.UserRoleCreateRequest;
import org.huhu.test.platform.model.response.UserRoleQueryResponse;
import org.huhu.test.platform.model.vo.UserRoleDeleteVo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TestPlatformUserRoleService {

    /**
     * 删除测试平台用户角色
     *
     * @param vo 值对象
     */
    Mono<Void> deleteTestPlatformUseRole(UserRoleDeleteVo vo);

    /**
     * 创建测试平台用户角色
     *
     * @param request 请求
     */
    Mono<Void> createTestPlatformUserRole(UserRoleCreateRequest request);

    /**
     * 查询测试平台用户角色
     *
     * @param username 用户名
     */
    Flux<UserRoleQueryResponse> queryTestPlatformUserRole(String username);

}
