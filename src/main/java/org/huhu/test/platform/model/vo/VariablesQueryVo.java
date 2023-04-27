package org.huhu.test.platform.model.vo;

import reactor.core.publisher.Mono;

/**
 * 测试平台变量查询值对象
 *
 * @param username 用户名
 * @param profileName 环境名
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformVariableController#queryVariables(Mono)
 * @since 0.0.1
 */
public record VariablesQueryVo(
        String username,
        String profileName) {}
