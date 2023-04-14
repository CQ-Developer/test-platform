package org.huhu.test.platform.model.table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 测试平台全局变量表单元测试
 *
 * @author 18551681083@163.com
 * @see TestPlatformGlobalVariable
 * @since 0.0.1
 */
class TestPlatformGlobalVariableTest {

    @Test
    void testPlatformGlobalVariable() {
        var variable = new TestPlatformGlobalVariable();
        variable.setVariableId(0L);
        variable.setVariableName("url");
        variable.setVariableValue("http://some.path");
        variable.setVariableDescription(null);
        variable.setUsername("root");
        assertEquals(0L, variable.getVariableId());
        assertEquals("url", variable.getVariableName());
        assertEquals("http://some.path", variable.getVariableValue());
        assertNull(variable.getVariableDescription());
        assertEquals("root", variable.getUsername());
    }

}
