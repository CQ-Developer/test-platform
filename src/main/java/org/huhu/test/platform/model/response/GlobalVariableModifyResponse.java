package org.huhu.test.platform.model.response;

import org.huhu.test.platform.model.table.TestPlatformGlobalVariable;
import reactor.core.publisher.Mono;

/**
 * 测试平台全局变量变更响应
 *
 * @param variableName 变量名
 * @param variableValue 变量值
 * @param variableDescription 变量描述
 *
 * @see org.huhu.test.platform.controller.TestPlatformGlobalVariableController#create(Mono, Mono)
 * @see org.huhu.test.platform.controller.TestPlatformGlobalVariableController#update(Mono, String, Mono)
 */
public record GlobalVariableModifyResponse(
        String variableName,
        String variableValue,
        String variableDescription) {

    public static GlobalVariableModifyResponse from(TestPlatformGlobalVariable globalVariable) {
        return new GlobalVariableModifyResponse(
                globalVariable.getVariableName(),
                globalVariable.getVariableValue(),
                globalVariable.getVariableDescription());
    }

}
