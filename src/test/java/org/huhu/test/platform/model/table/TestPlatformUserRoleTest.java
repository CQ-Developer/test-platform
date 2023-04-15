package org.huhu.test.platform.model.table;

import org.junit.jupiter.api.Test;

import static org.huhu.test.platform.constant.TestPlatformRoleLevel.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试平台用户表单元测试
 *
 * @author 18551681083@163.com
 * @see TestPlatformUserRole
 * @since 0.0.1
 */
class TestPlatformUserRoleTest {

    @Test
    void testPlatformUserRole() {
        var userRole = new TestPlatformUserRole();
        userRole.setRoleId(0L);
        userRole.setRoleLevel(ADMIN);
        userRole.setUsername("root");
        assertEquals(0L, userRole.getRoleId());
        assertEquals(ADMIN, userRole.getRoleLevel());
        assertEquals("root", userRole.getUsername());
    }

}