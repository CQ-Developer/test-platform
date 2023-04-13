package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.huhu.test.platform.constant.TestPlatformRoleName;
import reactor.core.publisher.Mono;

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.USERNAME;

/**
 * 测试平台用户角色创建请求
 *
 * @param username 用户名
 * @param roleName 角色名
 *
 * @see org.huhu.test.platform.controller.TestPlatformUserRoleController#createUserRole(Mono)
 */
public record UserRoleCreateRequest(
        @NotBlank
        @Pattern(regexp = USERNAME)
        String username,

        @NotNull
        TestPlatformRoleName roleName) {}
