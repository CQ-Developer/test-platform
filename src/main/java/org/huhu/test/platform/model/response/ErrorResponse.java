package org.huhu.test.platform.model.response;

import org.huhu.test.platform.constant.TestPlatformError;

import static org.huhu.test.platform.constant.TestPlatformError.CLIENT_ERROR;
import static org.huhu.test.platform.constant.TestPlatformError.SERVER_ERROR;

public class ErrorResponse {

    private int code;

    private String message;

    private ErrorResponse(TestPlatformError error) {
        this.code = TestPlatformError.getErrorDetail(error, TestPlatformError::getErrorCode);
        this.message = TestPlatformError.getErrorDetail(error, TestPlatformError::getErrorMessage);
    }

    public static ErrorResponse clientError() {
        return new ErrorResponse(CLIENT_ERROR);
    }

    public static ErrorResponse serverError() {
        return new ErrorResponse(SERVER_ERROR);
    }

    public ErrorResponse withError(TestPlatformError error) {
        this.code = TestPlatformError.getErrorDetail(error, TestPlatformError::getErrorCode);
        this.message = TestPlatformError.getErrorDetail(error, TestPlatformError::getErrorMessage);
        return this;
    }

    public ErrorResponse withCode(int code) {
        this.code = code;
        return this;
    }

    public ErrorResponse withCode(TestPlatformError error) {
        this.code = TestPlatformError.getErrorDetail(error, TestPlatformError::getErrorCode);
        return this;
    }

    public ErrorResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    public ErrorResponse withMessage(TestPlatformError error) {
        this.message = TestPlatformError.getErrorDetail(error, TestPlatformError::getErrorMessage);
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
