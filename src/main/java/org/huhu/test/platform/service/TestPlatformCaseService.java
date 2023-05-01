package org.huhu.test.platform.service;

import org.huhu.test.platform.model.response.CaseQueryResponse;
import reactor.core.publisher.Flux;

/**
 * 测试平台测试用例 {@link org.springframework.stereotype.Service} 接口
 *
 * @author 18551681083@163.com
 * @since 0.0.2
 */
public interface TestPlatformCaseService {

    /**
     * 查询测试用例
     *
     * @param username 用户名
     */
    Flux<CaseQueryResponse> queryTestPlatformCase(String username);

}
