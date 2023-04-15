package org.huhu.test.platform.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.huhu.test.platform.exception.ClientTestPlatformException;

/**
 * 测试平台角色枚举类
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public enum TestPlatformRoleLevel {

    /**
     * 用户
     */
    USER(1),

    /**
     * 开发
     */
    DEV(2),

    /**
     * 管理员
     */
    ADMIN(3);

    /**
     * 权限级别
     */
    final int level;

    /**
     * 构造器
     *
     * @param level 权限级别
     */
    TestPlatformRoleLevel(int level) {
        this.level = level;
    }

    /**
     * 获取角色权限级别
     */
    @JsonValue
    public int getLevel() {
        return level;
    }

    @JsonCreator
    public static TestPlatformRoleLevel deserialize(int roleLevel) {
        for (TestPlatformRoleLevel testPlatformRoleLevel : values()) {
            if (testPlatformRoleLevel.level == roleLevel) {
                return testPlatformRoleLevel;
            }
        }
        throw new ClientTestPlatformException("client role level invalid");
    }

}
