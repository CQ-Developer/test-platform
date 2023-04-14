package org.huhu.test.platform.model.request;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试平台用户延期请求单元测试
 *
 * @author 18551681083@163.com
 * @see UserRenewRequest
 * @since 0.0.1
 */
class UserRenewRequestTest {

    @Test
    void testUserRenewRequest() {
        var request = new UserRenewRequest("Jack", LocalDateTime.of(2099, 1, 1, 1, 1, 1));
        assertEquals("Jack", request.username());
        assertEquals(LocalDateTime.parse("2099-01-01T01:01:01"), request.expiredTime());
    }

}
