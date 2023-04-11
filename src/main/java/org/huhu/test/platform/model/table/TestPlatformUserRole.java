package org.huhu.test.platform.model.table;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("t_test_user_role")
public class TestPlatformUserRole {

    @Id
    @Column("role_id")
    private Long roleId;

    private String username;

    @Column("user_role")
    private String userRole;

    public TestPlatformUserRole() {}

    public TestPlatformUserRole(String username) {
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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

}
