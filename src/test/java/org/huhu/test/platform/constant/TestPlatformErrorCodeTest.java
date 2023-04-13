package org.huhu.test.platform.constant;

import org.junit.jupiter.api.Test;

import static org.huhu.test.platform.constant.TestPlatformErrorCode.CLIENT_ERROR;
import static org.huhu.test.platform.constant.TestPlatformErrorCode.SERVER_ERROR;
import static org.huhu.test.platform.constant.TestPlatformErrorCode.SERVICE_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试平台错误码单元测试
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.constant.TestPlatformErrorCode
 * @since 0.0.1
 */
class TestPlatformErrorCodeTest {

    @Test
    void testClientError() {
        assertEquals(1000, CLIENT_ERROR.getErrorCode());
        assertEquals("client error", CLIENT_ERROR.getErrorMessage());
    }

    @Test
    void testThirdPartyServiceError() {
        assertEquals(2000, SERVICE_ERROR.getErrorCode());
        assertEquals("service error", SERVICE_ERROR.getErrorMessage());
    }

    @Test
    void testServerError() {
        assertEquals(3000, SERVER_ERROR.getErrorCode());
        assertEquals("server error", SERVER_ERROR.getErrorMessage());
    }

}
