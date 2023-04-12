package org.huhu.test.platform.model.table;

import org.huhu.test.platform.model.request.GlobalVariableModifyRequest;
import org.huhu.test.platform.model.vo.GlobalVariableCreateVo;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Optional;

@Table("t_test_global_variable")
public class TestPlatformGlobalVariable {

    @Id
    @Column("variable_id")
    private Long variableId;

    private String username;

    @Column("variable_name")
    private String variableName;

    @Column("variable_value")
    private String variableValue;

    @Column("variable_description")
    private String variableDescription;

    public static TestPlatformGlobalVariable from(GlobalVariableCreateVo vo) {
        var globalVariable = new TestPlatformGlobalVariable();
        globalVariable.setUsername(vo.username());
        GlobalVariableModifyRequest request = vo.request();
        globalVariable.setVariableName(request.variableName());
        globalVariable.setVariableValue(request.variableValue());
        Optional.ofNullable(request.variableDescription())
                .ifPresent(globalVariable::setVariableDescription);
        return globalVariable;
    }

    public Long getVariableId() {
        return variableId;
    }

    public void setVariableId(Long variableId) {
        this.variableId = variableId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
