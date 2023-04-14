package org.huhu.test.platform.exception;

import org.junit.jupiter.api.Test;

import static org.huhu.test.platform.constant.TestPlatformErrorCode.CLIENT_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试平台客户端异常单元测试
 *
 * @author 18551681083@163.com
 * @see ClientTestPlatformException
 * @since 0.0.1
 */
class ClientTestPlatformExceptionTest {

    @Test
    void getTestPlatformError() {
        var exception = new ClientTestPlatformException();
        assertEquals(CLIENT_ERROR, exception.getTestPlatformError());
    }

}