package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.USERNAME;

/**
 * 测试平台用户刷新请求
 *
 * @param username 用户名
 * @param expiredTime 过期时间
 */
public record UserRenewRequest(
        @NotBlank
        @Pattern(regexp = USERNAME)
        String username,

        LocalDateTime expiredTime) {}
