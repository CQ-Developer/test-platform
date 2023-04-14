package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.huhu.test.platform.constant.TestPlatformRoleName;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.PASSWORD;
import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.USERNAME;

/**
 * 测试平台用户创建请求
 *
 * @param username 用户名
 * @param password 密码
 * @param roles 用户角色
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

        @NotNull
        @Size(min = 1, max = 3)
        List<TestPlatformRoleName> roles) {}
