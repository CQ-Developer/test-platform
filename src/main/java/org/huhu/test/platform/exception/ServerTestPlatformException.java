package org.huhu.test.platform.exception;

import org.huhu.test.platform.constant.TestPlatformErrorCode;

import static org.huhu.test.platform.constant.TestPlatformErrorCode.SERVER_ERROR;

/**
 * 测试平台服务端错误
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public class ServerTestPlatformException extends TestPlatformException {

    public ServerTestPlatformException() {}

    public ServerTestPlatformException(Throwable throwable) {
        super(throwable);
    }

    public ServerTestPlatformException(String message) {
        super(message);
    }

    @Override
    public TestPlatformErrorCode getTestPlatformError() {
        return SERVER_ERROR;
    }

}
