package org.huhu.test.platform.model.request;

import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import org.junit.jupiter.api.Test;

import static org.huhu.test.platform.constant.TestPlatformRoleLevel.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试平台用户角色创建请求单元测试
 *
 * @author 18551681083@163.com
 * @see UserRoleCreateRequest
 * @since 0.0.1
 */
class UserRoleCreateRequestTest {

    @Test
    void testUserRoleCreateRequest() {
        var request = new UserRoleCreateRequest("Jack", USER);
        assertEquals("Jack", request.username());
        assertEquals(TestPlatformRoleLevel.valueOf("USER"), request.roleLevel());
    }

}
