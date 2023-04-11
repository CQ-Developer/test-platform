package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class GlobalVariableUpdateRequest {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9-_]+$")
    @Size(max = 32)
    private String variableName;

    @NotBlank
    @Size(max = 256)
    private String variableValue;

    @Size(max = 512)
    private String variableDescription;

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }

    public String getVariableDescription() {
        return variableDescription;
    }

    public void setVariableDescription(String variableDescription) {
        this.variableDescription = variableDescription;
    }

}
