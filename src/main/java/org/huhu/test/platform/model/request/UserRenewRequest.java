package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

/**
 * 测试平台用户刷新请求
 *
 * @param username 用户名
 * @param expiredTime 过期时间
 */
public record UserRenewRequest(
        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9-_]{4,16}$")
        String username,

        LocalDateTime expiredTime) {}
