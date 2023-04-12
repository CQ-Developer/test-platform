package org.huhu.test.platform.exception;

import org.huhu.test.platform.constant.TestPlatformError;

import static org.huhu.test.platform.constant.TestPlatformError.CLIENT_REQUEST_USERNAME_INVALID;

public class UserNotFoundException extends TestPlatformException {

    public UserNotFoundException() {
        super(CLIENT_REQUEST_USERNAME_INVALID);
    }

    @Override
    public TestPlatformError getTestPlatformError() {
        return CLIENT_REQUEST_USERNAME_INVALID;
    }

}
