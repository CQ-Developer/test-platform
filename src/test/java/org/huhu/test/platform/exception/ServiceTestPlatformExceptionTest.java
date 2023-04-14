package org.huhu.test.platform.exception;

import org.junit.jupiter.api.Test;

import static org.huhu.test.platform.constant.TestPlatformErrorCode.SERVICE_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试平台外部服务异常单元测试
 *
 * @author 18551681083@163.com
 * @see ServiceTestPlatformException
 * @since 0.0.1
 */
class ServiceTestPlatformExceptionTest {

    @Test
    void getTestPlatformError() {
        var exception = new ServiceTestPlatformException();
        assertEquals(SERVICE_ERROR, exception.getTestPlatformError());
        exception = new ServiceTestPlatformException(new RuntimeException());
        assertEquals(SERVICE_ERROR, exception.getTestPlatformError());
    }
}
