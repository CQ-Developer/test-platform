package org.huhu.test.platform.constant;

import org.junit.jupiter.api.Test;

import static org.huhu.test.platform.constant.TestPlatformRoleName.ADMIN;
import static org.huhu.test.platform.constant.TestPlatformRoleName.DEV;
import static org.huhu.test.platform.constant.TestPlatformRoleName.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试平台角色名单元测试
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.constant.TestPlatformRoleName
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
