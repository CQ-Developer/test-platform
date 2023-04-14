package org.huhu.test.platform.model.response;

import org.huhu.test.platform.constant.TestPlatformRoleName;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 测试平台用户查询响应
 *
 * @param username 用户名
 * @param userRoles 用户角色
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformUserController#queryUser()
 * @since 0.0.1
 */
public record UserQueryResponse(
        String username,
        List<TestPlatformRoleName> userRoles) {

    public static Mono<UserQueryResponse> from(
            GroupedFlux<String, TestPlatformUserRole> groupedFlux) {
        var username = Mono.just(groupedFlux.key());
        var roleNames = groupedFlux.map(TestPlatformUserRole::getRoleName).collectList();
        return Mono.zip(username, roleNames, UserQueryResponse::new);
    }

}
