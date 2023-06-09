package org.huhu.test.platform.model.response;

import org.huhu.test.platform.constant.TestPlatformRoleLevel;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 测试平台你用户详情查询响应
 *
 * @param username 用户名
 * @param roleLevels 用户角色
 * @param enabled 是否启用
 * @param locked 是否锁定
 * @param registerTime 注册时间
 * @param expiredTime 到期时间
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformUserController#queryUserDetail(String)
 * @since 0.0.1
 */
public record UserDetailQueryResponse(
        String username,
        List<TestPlatformRoleLevel> roleLevels,
        Boolean enabled,
        Boolean locked,
        LocalDateTime registerTime,
        LocalDateTime expiredTime) {}
