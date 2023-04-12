package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import reactor.core.publisher.Mono;

/**
 * 测试平台全局变量变更请求
 *
 * @param variableName 全局变量名
 * @param variableValue 全局变量值
 * @param variableDescription 全局变量描述
 *
 * @see org.huhu.test.platform.controller.TestPlatformGlobalVariableController#create(Mono, Mono)
 * @see org.huhu.test.platform.controller.TestPlatformGlobalVariableController#update(Mono, String, Mono)
 */
public record GlobalVariableModifyRequest(
        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9-_]{1,32}$")
        String variableName,

        @NotBlank @Size(max = 256)
        String variableValue,

        @Size(max = 512)
        String variableDescription) {}
