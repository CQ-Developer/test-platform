package org.huhu.test.platform.model.vo;

import reactor.core.publisher.Mono;

/**
 * 测试平台用户环境创建值对象
 *
 * @param username 用户名
 * @param profileName 环境名
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformUserProfileController#createUserProfile(Mono, Mono)
 * @since 0.0.1
 */
public record UserProfileModifyVo(
        String username,
        String profileName) {}