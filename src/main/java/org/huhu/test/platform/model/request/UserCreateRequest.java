package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.PASSWORD;
import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.USERNAME;

/**
 * 测试平台用户创建请求
 *
 * @param username 用户名
 * @param password 密码
 * @param roleLevel 用户角色
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformUserController#createUser(Mono)
 * @since 0.0.1
 */
public record UserCreateRequest(
        @NotBlank
        @Pattern(regexp = USERNAME)
        String username,

        @NotBlank
        @Pattern(regexp = PASSWORD)
        String password,

        @Size(max = 3)
        List<TestPlatformRoleLevel> roleLevel,

        LocalDateTime expiredTime) {}
