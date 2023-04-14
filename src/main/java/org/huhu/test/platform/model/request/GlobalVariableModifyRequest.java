package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import reactor.core.publisher.Mono;

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.VARIABLE_NAME;

/**
 * 测试平台全局变量变更请求
 *
 * @param variableName 全局变量名
 * @param variableValue 全局变量值
 * @param variableDescription 全局变量描述
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformGlobalVariableController#createGlobalVariable(Mono, Mono)
 * @see org.huhu.test.platform.controller.TestPlatformGlobalVariableController#updateGlobalVariable(Mono, String, Mono)
 * @since 0.0.1
 */
public record GlobalVariableModifyRequest(
        @NotBlank
        @Pattern(regexp = VARIABLE_NAME)
        String variableName,

        @NotBlank
        @Size(max = 256)
        String variableValue,

        @Size(max = 512)
        String variableDescription) {}
