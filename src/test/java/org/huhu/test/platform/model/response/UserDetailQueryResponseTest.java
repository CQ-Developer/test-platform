package org.huhu.test.platform.model.response;

import org.huhu.test.platform.model.table.TestPlatformUser;
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
        var user = new TestPlatformUser();
        user.setUsername("Jack");
        user.setEnabled(true);
        user.setLocked(false);
        user.setRegisterTime(LocalDateTime.of(2022, 1, 1, 1, 1, 1));
        user.setExpiredTime(LocalDateTime.of(2023, 1, 1, 1, 1, 1));
        var userRoles = List.of(USER, DEV);
        var response = UserDetailQueryResponse.from(user, userRoles);
        assertEquals("Jack", response.username());
        assertTrue(response.enabled());
        assertFalse(response.locked());
        assertEquals(LocalDateTime.parse("2022-01-01T01:01:01"), response.registerTime());
        assertEquals(LocalDateTime.parse("2023-01-01T01:01:01"), response.expiredTime());
        assertIterableEquals(List.of(USER, DEV), response.userRoles());
    }

}
