package org.huhu.test.platform.model.request;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.huhu.test.platform.constant.TestPlatformRoleLevel.DEV;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

/**
 * 测试平台用户创建请求体单元测试
 *
 * @author 18551681083@163.com
 * @see UserCreateRequest
 * @since 0.0.1
 */
class UserCreateRequestTest {

    @Test
    void testUserCreateRequest() {
        var request = new UserCreateRequest("Jack", "jack123", List.of(DEV));
        assertEquals("Jack", request.username());
        assertEquals("jack123", request.password());
        assertIterableEquals(List.of(DEV), request.roleLevel());
    }

}
