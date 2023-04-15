package org.huhu.test.platform.model.response;

import org.huhu.test.platform.constant.TestPlatformRoleLevel;

import java.util.List;

/**
 * 测试平台用户查询响应
 *
 * @param username 用户名
 * @param userRoles 用户角色
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformUserController#queryUser()
 * @since 0.0.1
 */
public record UserQueryResponse(
        String username,
        List<TestPlatformRoleLevel> userRoles) {}
