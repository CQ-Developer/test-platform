package org.huhu.test.platform.model.vo;

import org.huhu.test.platform.controller.TestPlatformGlobalVariableController;
import org.huhu.test.platform.model.request.GlobalVariableModifyRequest;
import reactor.core.publisher.Mono;

/**
 * 测试平台变量更新值对象
 *
 * @param username 用户名
 * @param variableName 变量名
 * @param request 变量变更请求
 *
 * @author 18551681083@163.com
 * @see TestPlatformGlobalVariableController#updateGlobalVariable(Mono, String, Mono)
 * @since 0.0.1
 */
public record GlobalVariableUpdateVo(String username, String variableName, GlobalVariableModifyRequest request) {}
