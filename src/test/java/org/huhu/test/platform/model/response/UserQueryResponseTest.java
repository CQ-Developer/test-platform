package org.huhu.test.platform.model.response;

import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.util.ConvertUtils;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Comparator;
import java.util.List;

import static org.huhu.test.platform.constant.TestPlatformRoleLevel.ADMIN;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.DEV;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.USER;

/**
 * 测试平台用户查询响应单元测试
 *
 * @author 18551681083@163.com
 * @see UserQueryResponse
 * @since 0.0.1
 */
class UserQueryResponseTest {

    @Test
    void testFromGroupedFlux() {
        var jackUser = new TestPlatformUserRole(USER, "jack");
        var jackDev = new TestPlatformUserRole(DEV, "jack");
        var rootAdmin = new TestPlatformUserRole(ADMIN, "root");
        var rootUser = new TestPlatformUserRole(USER, "root");
        var response = Flux
                .just(jackUser, jackDev, rootUser, rootAdmin)
                .groupBy(TestPlatformUserRole::getUsername)
                .flatMap(ConvertUtils::monoFrom)
                .sort(Comparator.comparing(UserQueryResponse::username));
        StepVerifier
                .create(response)
                .expectNext(new UserQueryResponse("jack", List.of(USER, DEV)))
                .expectNext(new UserQueryResponse("root", List.of(USER, ADMIN)))
                .expectComplete()
                .verify();
    }

}
