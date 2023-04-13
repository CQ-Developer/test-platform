package org.huhu.test.platform.exception;

import org.huhu.test.platform.constant.TestPlatformErrorCode;

/**
 * 测试平台统统一异常
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public abstract class TestPlatformException extends RuntimeException {

    public TestPlatformException() {}

    public TestPlatformException(Throwable throwable) {
        super(throwable);
    }

    public abstract TestPlatformErrorCode getTestPlatformError();

}
