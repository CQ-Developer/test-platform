package org.huhu.test.platform.constant;

/**
 * 测试平台角色枚举类
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public enum TestPlatformRoleName {

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
    TestPlatformRoleName(int level) {
        this.level = level;
    }

    /**
     * 获取角色权限级别
     */
    public int getLevel() {
        return level;
    }

}
