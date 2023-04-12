package org.huhu.test.platform.constant;

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
    ADMIN(10);

    final int level;

    TestPlatformRoleName(int level) {
        this.level = level;
    }

}
