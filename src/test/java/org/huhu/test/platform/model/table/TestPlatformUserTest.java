package org.huhu.test.platform.model.table;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 测试平台用户表单元测试
 *
 * @author 18551681083@163.com
 * @see TestPlatformUser
 * @since 0.0.1
 */
class TestPlatformUserTest {

    @Test
    void testPlatformUser() {
        var user = new TestPlatformUser();
        user.setUserId(0L);
        user.setUsername("root");
        user.setPassword("root_2023");
        user.setEnabled(true);
        user.setLocked(false);
        user.setRegisterTime(LocalDateTime.of(2023, 1, 1, 1, 1, 1));
        user.setExpiredTime(LocalDateTime.of(2024, 1, 1, 1, 1, 1));
        assertEquals(0L, user.getUserId());
        assertEquals("root", user.getUsername());
        assertEquals("root_2023", user.getPassword());
        assertTrue(user.getEnabled());
        assertFalse(user.getLocked());
        assertEquals(LocalDateTime.parse("2023-01-01T01:01:01"), user.getRegisterTime());
        assertEquals(LocalDateTime.parse("2024-01-01T01:01:01"), user.getExpiredTime());
    }

}
