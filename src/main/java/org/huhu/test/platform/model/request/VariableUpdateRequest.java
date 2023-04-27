package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.huhu.test.platform.constant.TestPlatformVariableScope;
import org.huhu.test.platform.controller.TestPlatformVariableController;
import reactor.core.publisher.Mono;

/**
 * 测试平台变量变更请求
 *
 * @param variableValue 变量值
 * @param variableDescription 变量描述
 *
 * @author 18551681083@163.com
 * @see TestPlatformVariableController#updateVariable(Mono, String, TestPlatformVariableScope, Mono)
 * @since 0.0.1
 */
public record VariableUpdateRequest(
        @NotBlank
        @Size(max = 256)
        String variableValue,

        @Size(max = 512)
        String variableDescription) {}
