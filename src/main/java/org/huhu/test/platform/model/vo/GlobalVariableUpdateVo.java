package org.huhu.test.platform.model.vo;

import org.huhu.test.platform.model.request.GlobalVariableUpdateRequest;
import reactor.util.function.Tuple3;

public record GlobalVariableUpdateVo(
        String username, Long variableId, String variableName,
        String variableValue, String variableDescription) {

    public static GlobalVariableUpdateVo build(Tuple3<String, Long, GlobalVariableUpdateRequest> tuple3) {
        return new GlobalVariableUpdateVo(tuple3.getT1(), tuple3.getT2(),
                tuple3.getT3().getVariableName(),
                tuple3.getT3().getVariableValue(),
                tuple3.getT3().getVariableDescription());
    }

}
