package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.huhu.test.platform.constant.TestPlatformRoleName;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 测试平台用户创建请求
 *
 * @param username 用户名
 * @param password 密码
 * @param roles 用户角色
 *
 * @see org.huhu.test.platform.controller.TestPlatformUserController#createUser(Mono)
 */
public record UserCreateRequest(
        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9-_]{4,16}$")
        String username,

        @NotBlank
        @Pattern(regexp = "^[\\w@#:-]{6,32}$")
        String password,

        @NotNull
        @Size(min = 1, max = 3)
        List<TestPlatformRoleName> roles) {}
