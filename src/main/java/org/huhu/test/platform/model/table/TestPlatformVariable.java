package org.huhu.test.platform.model.table;

import org.huhu.test.platform.constant.TestPlatformVariableScope;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("t_test_variable")
public class TestPlatformVariable {

    @Id
    @Column("variable_id")
    private Long variableId;

    @Column("variable_name")
    private String variableName;

    @Column("variable_value")
    private String variableValue;

    @Column("variable_scope")
    private TestPlatformVariableScope variableScope;

    @Column("variable_description")
    private String variableDescription;

    private String username;

    public Long getVariableId() {
        return variableId;
    }

    public void setVariableId(Long variableId) {
        this.variableId = variableId;
    }

    public String getUsername() {
        return username;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }

    public TestPlatformVariableScope getVariableScope() {
        return variableScope;
    }

    public void setVariableScope(TestPlatformVariableScope variableScope) {
        this.variableScope = variableScope;
    }

    public String getVariableDescription() {
        return variableDescription;
    }

    public void setVariableDescription(String variableDescription) {
        this.variableDescription = variableDescription;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVariableName() {
        return variableName;
    }

}
