package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import reactor.core.publisher.Mono;

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.USER_PROFILE;

/**
 * 测试平台用户环境创建请求
 *
 * @param profileName 环境名
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformUserProfileController#createUserProfile(Mono, Mono)
 * @since 0.0.1
 */
public record UserProfileModifyRequest(
        @NotBlank
        @Pattern(regexp = USER_PROFILE)
        String profileName) {}
