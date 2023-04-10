package org.huhu.test.platform.service;

import org.huhu.test.platform.model.response.QueryTestPlatformGlobalVariableResponse;
import reactor.core.publisher.Flux;

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
     * @param userId 用户编号
     * @param variableId 全局变量编号
     */
    Flux<Void> deleteTestPlatformGlobalVariable(Long userId, Long variableId);

}
