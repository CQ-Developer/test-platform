package org.huhu.test.platform.model.response;

import org.huhu.test.platform.constant.TestPlatformErrorCode;
import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.huhu.test.platform.exception.ServerTestPlatformException;
import org.huhu.test.platform.exception.ServiceTestPlatformException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试平台错误响应单元测试
 *
 * @author 18551681083@163.com
 * @see ErrorResponse
 * @since 0.0.1
 */
class ErrorResponseTest {

    @ParameterizedTest
    @ValueSource(strings = {"CLIENT_ERROR", "SERVICE_ERROR", "SERVER_ERROR"})
    void testFromTestPlatformErrorCode(String name) {
        var errorCode = TestPlatformErrorCode.valueOf(name);
        var response = ErrorResponse.from(errorCode);
        switch (errorCode) {
            case CLIENT_ERROR -> assertEquals(1000, response.code());
            case SERVICE_ERROR -> assertEquals(2000, response.code());
            case SERVER_ERROR -> assertEquals(3000, response.code());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"CLIENT_ERROR", "SERVICE_ERROR", "SERVER_ERROR"})
    void testFromTestPlatformException(String name) {
        var errorCode = TestPlatformErrorCode.valueOf(name);
        var exception = switch (errorCode) {
            case CLIENT_ERROR -> new ClientTestPlatformException();
            case SERVICE_ERROR -> new ServiceTestPlatformException();
            case SERVER_ERROR -> new ServerTestPlatformException();
        };
        var response = ErrorResponse.from(exception);
        if (exception instanceof ClientTestPlatformException) assertEquals(1000, response.code());
        if (exception instanceof ServiceTestPlatformException) assertEquals(2000, response.code());
        if (exception instanceof ServerTestPlatformException) assertEquals(3000, response.code());
    }

}
