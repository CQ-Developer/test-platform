package org.huhu.test.platform.model.response;

import org.huhu.test.platform.model.table.TestPlatformUserRole;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.util.List;

public record UserQueryResponse(String username, List<String> userRoles) {

    public static Mono<UserQueryResponse> build(
            GroupedFlux<String, TestPlatformUserRole> groupedFlux) {
        var username = Mono.just(groupedFlux.key());
        var userRoles = groupedFlux.map(TestPlatformUserRole::getUserRole).collectList();
        return Mono.zip(username, userRoles, UserQueryResponse::new);
    }

}
