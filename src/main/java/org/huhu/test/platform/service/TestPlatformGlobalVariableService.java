package org.huhu.test.platform.service;

import org.huhu.test.platform.model.response.GlobalVariableModifyResponse;
import org.huhu.test.platform.model.response.GlobalVariableQueryResponse;
import org.huhu.test.platform.model.vo.GlobalVariableCreateVo;
import org.huhu.test.platform.model.vo.GlobalVariableDeleteVo;
import org.huhu.test.platform.model.vo.GlobalVariableUpdateVo;
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
     * 创建测试平台全局变量
     *
     * @param vo 请求体
     */
    Mono<GlobalVariableModifyResponse> createTestPlatformGlobalVariable(GlobalVariableCreateVo vo);

    /**
     * 更新测试平台全局变量
     *
     * @param vo 请求体
     */
    Mono<GlobalVariableModifyResponse> updateTestPlatformGlobalVariable(GlobalVariableUpdateVo vo);

    /**
     * 删除测试平台全局变量
     *
     * @param vo 请求体
     */
    Mono<Integer> deleteTestPlatformGlobalVariable(GlobalVariableDeleteVo vo);

}
