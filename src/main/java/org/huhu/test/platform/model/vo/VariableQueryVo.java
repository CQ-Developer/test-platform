package org.huhu.test.platform.model.vo;

import reactor.core.publisher.Mono;

/**
 * 测试平台变量查询值对象
 *
 * @param username 用户名
 * @param variableName 变量名
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformVariableController#queryVariable(Mono, String)
 * @since 0.0.1
 */
public record VariableQueryVo(
        String username,
        String variableName) {}
