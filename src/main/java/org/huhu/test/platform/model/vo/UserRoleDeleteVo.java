package org.huhu.test.platform.model.vo;

import org.huhu.test.platform.constant.TestPlatformRoleName;

/**
 * 测试平台用户角色删除值对象
 *
 * @param username 用户名
 * @param roleName 角色名
 * @see org.huhu.test.platform.controller.TestPlatformUserRoleController#deleteUserRole(String)
 */
public record UserRoleDeleteVo(String username, TestPlatformRoleName roleName) {}
