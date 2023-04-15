package org.huhu.test.platform.constant;

import org.junit.jupiter.api.Test;

import static org.huhu.test.platform.constant.TestPlatformRoleLevel.ADMIN;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.DEV;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试平台角色級別单元测试
 *
 * @author 18551681083@163.com
 * @see Byte
 * @since 0.0.1
 */
class TestPlatformRoleNameTest {

    @Test
    void testUser() {
        assertEquals(1, USER.getLevel());
    }

    @Test
    void testDev() {
        assertEquals(2, DEV.getLevel());
    }

    @Test
    void testAdmin() {
        assertEquals(3, ADMIN.getLevel());
    }

}
