package org.huhu.test.platform.model.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试平台错误响应单元测试
 *
 * @author 18551681083@163.com
 * @see ErrorResponse
 * @since 0.0.1
 */
class ErrorResponseTest {

    @Test
    void testErrorResponse() {
        var response = new ErrorResponse(400, "bad request");
        assertEquals(400, response.code());
        assertEquals("bad request", response.message());
    }

}
