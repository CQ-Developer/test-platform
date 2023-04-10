package org.huhu.test.platform.service;

import org.huhu.test.platform.model.request.UpdateTestPlatformGlobalVariableRequest;
import org.huhu.test.platform.model.response.QueryTestPlatformGlobalVariableResponse;
import org.huhu.test.platform.model.response.UpdateTestPlatformGlobalVariableResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TestPlatformGlobalVariableService {

    /**
     * 查询测试平台所有全局变量
     *
     * @param userId 用户编号
     */
    Flux<QueryTestPlatformGlobalVariableResponse> queryTestPlatformGlobalVariables(Long userId);

    /**
     * 删除测试平台全局变量
     *
     * @param variableId 全局变量编号
     */
    Mono<Void> deleteTestPlatformGlobalVariable(Long variableId);

    /**
     * 更新测试平台全局变量
     *
     * @param request 请求体
     */
    Mono<UpdateTestPlatformGlobalVariableResponse> updateTestPlatformGlobalVariable(UpdateTestPlatformGlobalVariableRequest request);
}
