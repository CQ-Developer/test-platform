package org.huhu.test.platform.exception;

import org.huhu.test.platform.constant.TestPlatformErrorCode;

import static org.huhu.test.platform.constant.TestPlatformErrorCode.CLIENT_ERROR;

/**
 * 测试平台客户端错误
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public class ClientTestPlatformException extends TestPlatformException {

    public ClientTestPlatformException() {}

    public ClientTestPlatformException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public TestPlatformErrorCode getTestPlatformError() {
        return CLIENT_ERROR;
    }

}
