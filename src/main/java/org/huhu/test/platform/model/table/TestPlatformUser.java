package org.huhu.test.platform.model.table;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("t_test_user")
public record TestPlatformUser(
        @Id
        @Column("user_id")
        Long userId,

        String username,

        String password,

        Boolean enabled,

        Boolean locked,

        @Column("password_time")
        LocalDateTime passwordTime,

        @Column("register_time")
        LocalDateTime registerTime,

        @Column("expired_time")
        LocalDateTime expiredTime) {}
