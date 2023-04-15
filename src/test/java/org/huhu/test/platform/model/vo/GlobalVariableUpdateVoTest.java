package org.huhu.test.platform.model.vo;

import org.huhu.test.platform.model.request.GlobalVariableModifyRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 测试平台变量更新值对象单元测试
 *
 * @author 18551681083@163.com
 * @see GlobalVariableUpdateVo
 * @since 0.0.1
 */
class GlobalVariableUpdateVoTest {

    @Test
    void testGlobalVariableUpdateVo() {
        var request = new GlobalVariableModifyRequest("base_url", "http://some.path", null);
        var vo = new GlobalVariableUpdateVo("root", "url", request);
        assertEquals("root", vo.username());
        assertEquals("url", vo.variableName());
        assertEquals("base_url", vo.request().variableName());
        assertEquals("http://some.path", vo.request().variableValue());
        assertNull(vo.request().variableDescription());
    }

}
