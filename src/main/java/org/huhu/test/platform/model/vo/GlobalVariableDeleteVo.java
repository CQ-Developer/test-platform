package org.huhu.test.platform.model.vo;

import org.huhu.test.platform.controller.TestPlatformGlobalVariableController;
import reactor.core.publisher.Mono;

/**
 * 测试平台变量创建值对象
 *
 * @param username 用户名
 * @param variableName 变量名
 *
 * @author 18551681083@163.com
 * @see TestPlatformGlobalVariableController#deleteGlobalVariable(Mono, String)
 * @since 0.0.1
 */
public record GlobalVariableDeleteVo(String username, String variableName) {}
