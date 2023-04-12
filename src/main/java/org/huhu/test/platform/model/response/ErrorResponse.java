package org.huhu.test.platform.model.response;

import org.huhu.test.platform.constant.TestPlatformError;
import org.huhu.test.platform.exception.TestPlatformException;

public record ErrorResponse(Integer code, String message) {

    public static ErrorResponse from(TestPlatformError error) {
        return new ErrorResponse(error.getErrorCode(), error.getErrorMessage());
    }

    public static ErrorResponse from(TestPlatformException exception) {
        return from(exception.getTestPlatformError());
    }

}
