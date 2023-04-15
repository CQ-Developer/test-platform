package org.huhu.test.platform.model.vo;

import org.junit.jupiter.api.Test;

import static org.huhu.test.platform.constant.TestPlatformRoleLevel.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试平台用户角色删除值对象单元测试
 *
 * @author 18551681083@163.com
 * @see UserRoleDeleteVo
 * @since 0.0.1
 */
class UserRoleDeleteVoTest {

    @Test
    void testUserRoleDeleteVo() {
        var vo = new UserRoleDeleteVo("root", ADMIN);
        assertEquals("root", vo.username());
        assertEquals(ADMIN, vo.roleLevel());
    }

}
