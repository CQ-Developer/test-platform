package org.huhu.test.platform.service;

import org.huhu.test.platform.model.response.VariableQueryResponse;
import org.huhu.test.platform.model.vo.VariableCreateVo;
import org.huhu.test.platform.model.vo.VariableDeleteVo;
import org.huhu.test.platform.model.vo.VariableQueryVo;
import org.huhu.test.platform.model.vo.VariableUpdateVo;
import org.huhu.test.platform.model.vo.VariablesQueryVo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 测试平台变量 {@link org.springframework.stereotype.Service} 接口
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public interface TestPlatformVariableService {

    /**
     * 查询测试平台变量
     *
     * @param vo 请求体
     */
    Flux<VariableQueryResponse> queryTestPlatformVariables(VariablesQueryVo vo);

    /**
     * 查询测试平台变量
     *
     * @param vo 请求体
     */
    Flux<VariableQueryResponse> queryTestPlatformVariable(VariableQueryVo vo);

    /**
     * 创建测试平台变量
     *
     * @param vo 请求体
     */
    Mono<Void> createTestPlatformVariable(VariableCreateVo vo);

    /**
     * 更新测试平台变量
     *
     * @param vo 请求体
     */
    Mono<Void> updateTestPlatformVariable(VariableUpdateVo vo);

    /**
     * 删除测试平台变量
     *
     * @param vo 请求体
     */
    Mono<Void> deleteTestPlatformVariable(VariableDeleteVo vo);

}
