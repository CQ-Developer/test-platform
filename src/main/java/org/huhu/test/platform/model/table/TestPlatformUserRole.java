package org.huhu.test.platform.model.table;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("t_test_user_role")
public class TestPlatformUserRole {

    @Column("role_id")
    private Long roleId;

    @Column("user_id")
    private Long userId;

    @Column("user_role")
    private String userRole;

    public TestPlatformUserRole() {}

    public TestPlatformUserRole(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

}
