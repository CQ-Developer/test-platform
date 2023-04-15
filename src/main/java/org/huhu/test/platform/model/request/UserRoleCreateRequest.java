package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import reactor.core.publisher.Mono;

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.USERNAME;

/**
 * 测试平台用户角色创建请求
 *
 * @param username 用户名
 * @param roleLevel 角色級別
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformUserRoleController#createUserRole(Mono)
 * @since 0.0.1
 */
public record UserRoleCreateRequest(
        @NotBlank
        @Pattern(regexp = USERNAME)
        String username,

        @NotNull
        TestPlatformRoleLevel roleLevel) {}
