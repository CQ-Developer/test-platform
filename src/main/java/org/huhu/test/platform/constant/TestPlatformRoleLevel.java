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
     * 用户前缀
     */
    public static final String ROLE_PRE = "ROLE_";

    /**
     * 构造器
     *
     * @param level 权限级别
     */
    TestPlatformRoleLevel(int level) {
        this.level = level;
    }

    /**
     * 获取角色名称
     *
     * @see org.huhu.test.platform.configuration.TestPlatformSecurityConfiguration
     */
    public String getRoleName() {
        return ROLE_PRE + this.name();
    }

    /**
     * 获取角色权限级别
     */
    @JsonValue
    public int getLevel() {
        return level;
    }

    @JsonCreator
    public static TestPlatformRoleLevel deserialize(int role) {
        for (TestPlatformRoleLevel testPlatformRoleLevel : values()) {
            if (testPlatformRoleLevel.level == role) {
                return testPlatformRoleLevel;
            }
        }
        throw new ClientTestPlatformException("client role level invalid");
    }

}
