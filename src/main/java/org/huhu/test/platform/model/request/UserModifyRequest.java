package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.USERNAME;

/**
 * 测试平台用户变更请求
 *
 * @param username 用户名
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformUserController#enableUser(String)
 * @see org.huhu.test.platform.controller.TestPlatformUserController#disableUser(String)
 * @see org.huhu.test.platform.controller.TestPlatformUserController#lockUser(String)
 * @see org.huhu.test.platform.controller.TestPlatformUserController#unlockUser(String)
 * @since 0.0.1
 */
public record UserModifyRequest(
        @NotBlank
        @Pattern(regexp = USERNAME)
        String username) {
}
