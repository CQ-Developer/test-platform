package org.huhu.test.platform.model.vo;

import org.huhu.test.platform.model.request.GlobalVariableModifyRequest;
import reactor.core.publisher.Mono;

/**
 * 测试平台变量创建值对象
 *
 * @param username 用户名
 * @param request 变量变更请求
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformGlobalVariableController#createGlobalVariable(Mono, Mono)
 * @see org.huhu.test.platform.controller.TestPlatformGlobalVariableController#updateGlobalVariable(Mono, String, Mono)
 * @since 0.0.1
 */
public record GlobalVariableCreateVo(String username, GlobalVariableModifyRequest request) {}
