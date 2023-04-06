package org.huhu.test.platform.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TestPlatformRole {

    USER(0, "user"),

    DEV(1, "dev"),

    ADMIN(2, "admin");

    @JsonValue
    final int id;

    final String role;

    TestPlatformRole(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public static String getRoleName(TestPlatformRole testPlatformRole) {
        return testPlatformRole.role;
    }

}
