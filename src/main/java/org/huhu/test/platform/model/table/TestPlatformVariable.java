package org.huhu.test.platform.model.table;

import org.huhu.test.platform.constant.TestPlatformVariableScope;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("t_test_variable")
public record TestPlatformVariable(
        @Id
        @Column("variable_id")
        Long variableId,

        @Column("variable_name")
        String variableName,

        @Column("variable_value")
        String variableValue,

        @Column("variable_scope")
        TestPlatformVariableScope variableScope,

        @Column("variable _profile")
        String variableProfile,

        @Column("variable_description")
        String variableDescription,

        String username) {}
