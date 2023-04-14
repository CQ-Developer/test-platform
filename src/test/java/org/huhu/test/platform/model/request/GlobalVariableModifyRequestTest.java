package org.huhu.test.platform.model.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试平台全局变量变更请求体单元测试
 *
 * @author 18551681083@163.com
 * @see GlobalVariableModifyRequest
 * @since 0.0.1
 */
class GlobalVariableModifyRequestTest {

    @Test
    void testGlobalVariableModifyRequest() {
        var request = new GlobalVariableModifyRequest("url", "http://some.path", "base url for test");
        assertEquals("url", request.variableName());
        assertEquals("http://some.path", request.variableValue());
        assertEquals("base url for test", request.variableDescription());
    }

}
