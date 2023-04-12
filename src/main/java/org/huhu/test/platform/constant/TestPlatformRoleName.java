package org.huhu.test.platform.constant;

import java.util.Arrays;
import java.util.Comparator;

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

    final int level;

    TestPlatformRoleName(int level) {
        this.level = level;
    }

    int getLevel() {
        return level;
    }

    public static String[] all() {
        return from(values().length, Comparator.comparingInt(TestPlatformRoleName::getLevel));
    }

    public static String[] top(int n) {
        return from(n, Comparator.comparingInt(TestPlatformRoleName::getLevel).reversed());
    }

    private static String[] from(int n, Comparator<TestPlatformRoleName> comparator) {
        return Arrays.stream(values())
                     .sorted(comparator)
                     .limit(n)
                     .map(TestPlatformRoleName::name)
                     .toArray(String[]::new);
    }

}
