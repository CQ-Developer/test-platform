package org.huhu.test.platform.model.vo;

import org.huhu.test.platform.model.request.GlobalVariableUpdateRequest;
import reactor.util.function.Tuple3;

public record UpdateGlobalVariableVo(
        String username, Long variableId, String variableName,
        String variableValue, String variableDescription) {

    public static UpdateGlobalVariableVo fromTuple3(Tuple3<String, Long, GlobalVariableUpdateRequest> tuple3) {
        GlobalVariableUpdateRequest request = tuple3.getT3();
        String variableName = request.getVariableName();
        String variableValue = request.getVariableValue();
        String variableDescription = request.getVariableDescription();
        return new UpdateGlobalVariableVo(tuple3.getT1(), tuple3.getT2(), variableName, variableValue, variableDescription);
    }

}
