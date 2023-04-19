package org.huhu.test.platform.model.vo;

import org.huhu.test.platform.constant.TestPlatformRoleLevel;

/**
 * 测试平台用户角色删除值对象
 *
 * @param username 用户名
 * @param roleLevel 角色級別
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformUserRoleController#deleteUserRole(String, TestPlatformRoleLevel)
 * @since 0.0.1
 */
public record UserRoleDeleteVo(
        String username,
        TestPlatformRoleLevel roleLevel) {}
