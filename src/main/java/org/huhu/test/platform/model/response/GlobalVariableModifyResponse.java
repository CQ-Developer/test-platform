package org.huhu.test.platform.model.response;

import org.huhu.test.platform.model.table.TestPlatformGlobalVariable;

public record GlobalVariableModifyResponse(String variableName, String variableValue, String variableDescription) {

    public static GlobalVariableModifyResponse build(TestPlatformGlobalVariable globalVariable) {
        return new GlobalVariableModifyResponse(globalVariable.getVariableName(),
                globalVariable.getVariableValue(), globalVariable.getVariableDescription());
    }

}
