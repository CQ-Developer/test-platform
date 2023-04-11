package org.huhu.test.platform.service;

import org.huhu.test.platform.model.response.GlobalVariableQueryResponse;
import org.huhu.test.platform.model.response.GlobalVariableUpdateResponse;
import org.huhu.test.platform.model.vo.UpdateGlobalVariableVo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TestPlatformGlobalVariableService {

    /**
     * 查询测试平台所有全局变量
     *
     * @param username 用户名
     */
    Flux<GlobalVariableQueryResponse> queryTestPlatformGlobalVariables(String username);

    /**
     * 删除测试平台全局变量
     *
     * @param variableId 全局变量编号
     */
    Mono<Void> deleteTestPlatformGlobalVariable(Long variableId);

    /**
     * 更新测试平台全局变量
     *
     * @param vo 请求体
     */
    Mono<GlobalVariableUpdateResponse> updateTestPlatformGlobalVariable(UpdateGlobalVariableVo vo);

}
