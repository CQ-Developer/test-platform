package org.huhu.test.platform.model.response;

import org.huhu.test.platform.model.table.TestPlatformGlobalVariable;
import reactor.core.publisher.Mono;

/**
 * 测试平台全局变量查询响应
 *
 * @param variableName 变量名
 * @param variableValue 变量值
 * @param variableDescription 变量描述
 *
 * @see org.huhu.test.platform.controller.TestPlatformGlobalVariableController#query(Mono)
 */
public record GlobalVariableQueryResponse(
        String variableName,
        String variableValue,
        String variableDescription) {

    public static GlobalVariableQueryResponse from(TestPlatformGlobalVariable globalVariable) {
        return new GlobalVariableQueryResponse(
                globalVariable.getVariableName(),
                globalVariable.getVariableValue(),
                globalVariable.getVariableDescription());
    }

}
