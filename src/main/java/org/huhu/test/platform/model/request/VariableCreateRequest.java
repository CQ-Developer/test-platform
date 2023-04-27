package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.huhu.test.platform.constant.TestPlatformVariableScope;
import org.huhu.test.platform.controller.TestPlatformVariableController;
import reactor.core.publisher.Mono;

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.VARIABLE_NAME;

/**
 * 测试平台变量创建请求
 *
 * @param variableName 变量名
 * @param variableValue 变量值
 * @param variableScope 变量作用域
 * @param variableDescription 变量描述
 *
 * @author 18551681083@163.com
 * @see TestPlatformVariableController#createVariable(Mono, Mono)
 * @since 0.0.1
 */
public record VariableCreateRequest(
        @NotBlank
        @Pattern(regexp = VARIABLE_NAME)
        String variableName,

        @NotBlank
        @Size(max = 256)
        String variableValue,

        @NotNull
        TestPlatformVariableScope variableScope,

        @Size(max = 512)
        String variableDescription) {}
