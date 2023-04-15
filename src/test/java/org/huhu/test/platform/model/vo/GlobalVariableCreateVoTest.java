package org.huhu.test.platform.model.vo;

import org.huhu.test.platform.model.request.GlobalVariableModifyRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 测试平台变量创建值对象单元测试
 *
 * @author 18551681083@163.com
 * @see GlobalVariableCreateVo
 * @since 0.0.1
 */
class GlobalVariableCreateVoTest {

    @Test
    void testGlobalVariableCreateVo() {
        var request = new GlobalVariableModifyRequest("url", "http://some.path", null);
        var vo = new GlobalVariableCreateVo("root", request);
        assertEquals("root", vo.username());
        assertEquals("url", vo.request().variableName());
        assertEquals("http://some.path", vo.request().variableValue());
        assertNull(vo.request().variableDescription());
    }

}
