package org.huhu.test.platform.model.vo;

import org.huhu.test.platform.constant.TestPlatformVariableScope;
import org.huhu.test.platform.controller.TestPlatformVariableController;
import reactor.core.publisher.Mono;

/**
 * 测试平台变量创建值对象
 *
 * @param username 用户名
 * @param variableName 变量名
 * @param variableScope 变量作用域
 *
 * @author 18551681083@163.com
 * @see TestPlatformVariableController#deleteVariable(Mono, String, TestPlatformVariableScope)
 * @since 0.0.1
 */
public record VariableDeleteVo(
        String username,
        String variableName,
        TestPlatformVariableScope variableScope) {}
