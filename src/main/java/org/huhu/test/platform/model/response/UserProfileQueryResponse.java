package org.huhu.test.platform.model.response;

import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 测试平台用户环境查询响应
 *
 * @param active 启用的环境
 * @param candidates 所有可选的环境
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformUserProfileController#queryUserProfile(Mono)
 * @since 0.0.1
 */
public record UserProfileQueryResponse(
        String active,
        List<String> candidates) {}
