package org.huhu.test.platform.model.table;

import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("t_test_user_role")
public record TestPlatformUserRole(
        @Id
        @Column("role_id")
        Long roleId,

        @Column("role_level")
        TestPlatformRoleLevel roleLevel,

        String username) {}
