package org.huhu.test.platform.model.table;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("t_test_user_profile")
public record TestPlatformUserProfile(
        @Id
        @Column("profile_id")
        Long profileId,

        @Column("profile_name")
        String profileName,

        String username) {}
