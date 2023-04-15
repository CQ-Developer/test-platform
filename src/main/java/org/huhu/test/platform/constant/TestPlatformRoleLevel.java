package org.huhu.test.platform.constant;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.huhu.test.platform.exception.ServerTestPlatformException;

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
    @JsonProperty("1")
    USER((byte) 1),

    /**
     * 开发
     */
    @JsonProperty("2")
    DEV((byte) 2),

    /**
     * 管理员
     */
    @JsonProperty("3")
    ADMIN((byte) 3);

    /**
     * 权限级别
     */
    final byte level;

    /**
     * 构造器
     *
     * @param level 权限级别
     */
    TestPlatformRoleLevel(byte level) {
        this.level = level;
    }

    /**
     * 获取角色权限级别
     */
    public byte getLevel() {
        return level;
    }

    public static TestPlatformRoleLevel fromLevel(byte level) {
        for (TestPlatformRoleLevel item : values()) {
            var roleLevel = item.getLevel();
            if (roleLevel == level) {
                return item;
            }
        }
        throw new ServerTestPlatformException("role level invalid");
    }

}
