package org.huhu.test.platform.model.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 测试平台全局变量查询响应单元测试
 *
 * @author 18551681083@163.com
 * @see GlobalVariableQueryResponse
 * @since 0.0.1
 */
class GlobalVariableQueryResponseTest {

    @Test
    void testFromGlobalVariableQueryResponse() {
        var response = new GlobalVariableQueryResponse("url", "http://some.path", null);
        assertEquals("url", response.variableName());
        assertEquals("http://some.path", response.variableValue());
        assertNull(response.variableDescription());
    }

}
