package org.huhu.test.platform.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdateTestPlatformGlobalVariableRequest {

    @JsonIgnore
    private Long variableId;

    @NotBlank
    @Pattern(regexp = "")
    @Size(min = 1, max = 128)
    private String variableName;

    @NotBlank
    @Pattern(regexp = "")
    @Size(min = 1, max = 256)
    private String variableValue;

    @Size(max = 512)
    private String variableDescription;

    public Long getVariableId() {
        return variableId;
    }

    public void setVariableId(Long variableId) {
        this.variableId = variableId;
    }

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
