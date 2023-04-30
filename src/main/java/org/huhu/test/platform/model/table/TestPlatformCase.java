package org.huhu.test.platform.model.table;

import org.huhu.test.platform.constant.TestPlatformCaseMethod;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("t_test_case")
public record TestPlatformCase(
        @Id
        @Column("case_id")
        Long caseId,

        @Column("case_name")
        String caseName,

        @Column("case_method")
        TestPlatformCaseMethod caseMethod,

        @Column("case_url")
        String caseUri,

        String username) {}
