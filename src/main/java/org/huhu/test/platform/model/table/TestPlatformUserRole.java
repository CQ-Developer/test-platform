package org.huhu.test.platform.model.table;

import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("t_test_user_role")
public class TestPlatformUserRole {

    @Id
    @Column("role_id")
    private Long roleId;

    @Column("role_level")
    private TestPlatformRoleLevel roleLevel;

    private String username;

    public TestPlatformUserRole() {}

    public TestPlatformUserRole(TestPlatformRoleLevel roleLevel, String username) {
        this.roleLevel = roleLevel;
        this.username = username;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TestPlatformRoleLevel getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(TestPlatformRoleLevel roleLevel) {
        this.roleLevel = roleLevel;
    }

}
