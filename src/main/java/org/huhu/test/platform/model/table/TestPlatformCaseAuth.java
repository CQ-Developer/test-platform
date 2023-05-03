package org.huhu.test.platform.model.table;

import org.huhu.test.platform.constant.TestPlatformCaseAuthType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public record TestPlatformCaseAuth(
        @Id
        @Column("auth_id")
        Long authId,

        @Column("auth_type")
        TestPlatformCaseAuthType authType,

        @Column("auth_content")
        String authContent,

        @Column("case_name")
        String caseName,

        String username) {}
