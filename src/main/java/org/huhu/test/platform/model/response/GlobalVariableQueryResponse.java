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
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformGlobalVariableController#queryGlobalVariable(Mono)
 * @since 0.0.1
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
