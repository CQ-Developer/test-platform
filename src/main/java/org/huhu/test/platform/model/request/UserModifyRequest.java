package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.huhu.test.platform.constant.TestPlatformUserModifyPath;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.USERNAME;

/**
 * 测试平台用户变更请求
 *
 * @param username 用户名
 * @param newTime 新时间
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformUserController#modifyUser(TestPlatformUserModifyPath, Mono)
 * @since 0.0.1
 */
public record UserModifyRequest(
        @NotBlank
        @Pattern(regexp = USERNAME)
        String username,

        LocalDateTime newTime) {}
