package org.huhu.test.platform.model.response;

import org.huhu.test.platform.model.table.TestPlatformGlobalVariable;

public record GlobalVariableQueryResponse(String variableName, String variableValue, String variableDescription) {

    public static GlobalVariableQueryResponse build(TestPlatformGlobalVariable globalVariable) {
        return new GlobalVariableQueryResponse(globalVariable.getVariableName(),
                globalVariable.getVariableValue(), globalVariable.getVariableDescription());
    }

}
