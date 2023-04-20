package org.huhu.test.platform.model.response;

import org.huhu.test.platform.constant.TestPlatformVariableScope;
import reactor.core.publisher.Mono;

/**
 * 测试平台变量查询响应
 *
 * @param variableName 变量名
 * @param variableValue 变量值
 * @param variableScope 变量作用域
 * @param variableDescription 变量描述
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformVariableController#queryVariable(Mono)
 * @see org.huhu.test.platform.controller.TestPlatformVariableController#queryVariable(Mono, String)
 * @since 0.0.1
 */
public record VariableQueryResponse(
        String variableName,
        String variableValue,
        TestPlatformVariableScope variableScope,
        String variableDescription) {}
