package org.huhu.test.platform.model.response;

import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import reactor.core.publisher.Mono;

/**
 * 测试平台用户角色查询响应
 *
 * @param roleName 角色名
 * @param roleLevel 角色级别
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformUserRoleController#queryUserRole(String)
 * @see org.huhu.test.platform.controller.TestPlatformUserRoleController#queryUserRole(Mono)
 * @since 0.0.1
 */
public record UserRoleQueryResponse(
        String roleName,
        TestPlatformRoleLevel roleLevel) {}
