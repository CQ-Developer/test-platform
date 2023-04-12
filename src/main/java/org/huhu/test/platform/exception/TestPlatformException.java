package org.huhu.test.platform.exception;

import org.huhu.test.platform.constant.TestPlatformError;

/**
 * 用户名无法定位到一个用户
 */
public abstract class TestPlatformException extends RuntimeException {

    protected TestPlatformException(TestPlatformError error) {
        super(error.getErrorMessage());
    }

    public abstract TestPlatformError getTestPlatformError();

}
