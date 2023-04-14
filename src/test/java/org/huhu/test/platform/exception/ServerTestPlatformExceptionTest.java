package org.huhu.test.platform.exception;

import org.junit.jupiter.api.Test;

import static org.huhu.test.platform.constant.TestPlatformErrorCode.SERVER_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试平台服务端异常单元测试
 *
 * @author 18551681083@163.com
 * @see ClientTestPlatformException
 * @since 0.0.1
 */
class ServerTestPlatformExceptionTest {

    @Test
    void getTestPlatformError() {
        var exception = new ServerTestPlatformException();
        assertEquals(SERVER_ERROR, exception.getTestPlatformError());
        exception = new ServerTestPlatformException(new RuntimeException());
        assertEquals(SERVER_ERROR, exception.getTestPlatformError());
    }

}
