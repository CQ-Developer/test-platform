package org.huhu.test.platform.model.table;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("t_test_user_authority")
public class TestPlatformUserRole {

    @Id
    private Long authorityId;

    private Long userId;

    private String userRole;

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
