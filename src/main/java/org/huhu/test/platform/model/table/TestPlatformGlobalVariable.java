package org.huhu.test.platform.model.table;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("t_test_global_variable")
public class TestPlatformGlobalVariable {

    @Column("variable_id")
    private Long variableId;

    @Column("user_id")
    private Long userId;

    @Column("variable_name")
    private String variableName;

    @Column("variable_value")
    private String variableValue;

    @Column("variable_description")
    private String variableDescription;

    public TestPlatformGlobalVariable() {
    }

    public TestPlatformGlobalVariable(Long userId) {
        this.userId = userId;
    }

    public Long getVariableId() {
        return variableId;
    }

    public void setVariableId(Long variableId) {
        this.variableId = variableId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getVariableName() {
        return variableName;
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

    public String getVariableDescription() {
        return variableDescription;
    }

    public void setVariableDescription(String variableDescription) {
        this.variableDescription = variableDescription;
    }

}
