package org.huhu.test.platform.model.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.huhu.test.platform.constant.TestPlatformRoleName.DEV;
import static org.huhu.test.platform.constant.TestPlatformRoleName.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 测试平台用户详情查询响应单元测试
 *
 * @author 18551681083@163.com
 * @see UserDetailQueryResponse
 * @since 0.0.1
 */
class UserDetailQueryResponseTest {

    @Test
    void testFromTestPlatformUserAndTestPlatformRoleNames() {
        var response = new UserDetailQueryResponse("Jack", List.of(USER, DEV), true, false,
                LocalDateTime.of(2022, 1, 1, 1, 1, 1), LocalDateTime.of(2023, 1, 1, 1, 1, 1));
        assertEquals("Jack", response.username());
        assertTrue(response.enabled());
        assertFalse(response.locked());
        assertEquals(LocalDateTime.parse("2022-01-01T01:01:01"), response.registerTime());
        assertEquals(LocalDateTime.parse("2023-01-01T01:01:01"), response.expiredTime());
        assertIterableEquals(List.of(USER, DEV), response.userRoles());
    }

}
