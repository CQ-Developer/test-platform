package org.huhu.test.platform.model.vo;

import org.huhu.test.platform.controller.TestPlatformVariableController;
import org.huhu.test.platform.model.request.VariableCreateRequest;
import reactor.core.publisher.Mono;

/**
 * 测试平台变量创建值对象
 *
 * @param username 用户名
 * @param variableProfile 变量环境
 * @param request 变量变更请求
 *
 * @author 18551681083@163.com
 * @see TestPlatformVariableController#createVariable(Mono, Mono)
 * @since 0.0.1
 */
public record VariableCreateVo(
        String username,
        String variableProfile,
        VariableCreateRequest request) {}
