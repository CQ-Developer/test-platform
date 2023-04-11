package org.huhu.test.platform.model.vo;

import org.huhu.test.platform.model.request.GlobalVariableModifyRequest;
import reactor.util.function.Tuple3;

public record GlobalVariableUpdateVo(String username, String variableName, GlobalVariableModifyRequest request) {

    public static GlobalVariableUpdateVo build(Tuple3<String, String, GlobalVariableModifyRequest> tuple3) {
        return new GlobalVariableUpdateVo(tuple3.getT1(), tuple3.getT2(), tuple3.getT3());
    }

}
