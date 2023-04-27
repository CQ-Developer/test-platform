package org.huhu.test.platform.model.vo;

import org.huhu.test.platform.constant.TestPlatformVariableScope;
import org.huhu.test.platform.model.request.VariableUpdateRequest;
import reactor.core.publisher.Mono;

/**
 * 测试平台变量更新值对象
 *
 * @param username 用户名
 * @param variableProfile 变量环境
 * @param variableName 变量名
 * @param variableScope 变量作用域
 * @param request 变量变更请求
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformVariableController#updateVariable(Mono, String, TestPlatformVariableScope, Mono)
 * @since 0.0.1
 */
public record VariableUpdateVo(
        String username,
        String variableProfile,
        String variableName,
        TestPlatformVariableScope variableScope,
        VariableUpdateRequest request) {}
