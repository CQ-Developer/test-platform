package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record GlobalVariableModifyRequest(
        @NotBlank @Pattern(regexp = "^[A-Za-z0-9-_]{1,32}$") @Size(max = 32) String variableName,
        @NotBlank @Size(max = 256) String variableValue,
        @Size(max = 512) String variableDescription) {}
