package org.huhu.test.platform.model.vo;

import org.huhu.test.platform.model.request.GlobalVariableCreateRequest;

public record GlobalVariableCreateVo(
        String username, String variableName,
        String variableValue, String variableDescription) {

    public static GlobalVariableCreateVo build(String username, GlobalVariableCreateRequest request) {
        return new GlobalVariableCreateVo(username, request.getVariableName(),
                request.getVariableValue(), request.getVariableDescription());
    }

}
