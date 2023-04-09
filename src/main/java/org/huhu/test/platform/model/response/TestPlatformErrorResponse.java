package org.huhu.test.platform.model.response;

import org.huhu.test.platform.constant.TestPlatformError;

import static org.huhu.test.platform.constant.TestPlatformError.CLIENT_ERROR;
import static org.huhu.test.platform.constant.TestPlatformError.SERVER_ERROR;

public class TestPlatformErrorResponse {

    private int code;

    private String message;

    private TestPlatformErrorResponse(TestPlatformError error) {
        this.code = TestPlatformError.getErrorDetail(error, TestPlatformError::getErrorCode);
        this.message = TestPlatformError.getErrorDetail(error, TestPlatformError::getErrorMessage);
    }

    public static TestPlatformErrorResponse clientError() {
        return new TestPlatformErrorResponse(CLIENT_ERROR);
    }

    public static TestPlatformErrorResponse serverError() {
        return new TestPlatformErrorResponse(SERVER_ERROR);
    }

    public TestPlatformErrorResponse withError(TestPlatformError error) {
        this.code = TestPlatformError.getErrorDetail(error, TestPlatformError::getErrorCode);
        this.message = TestPlatformError.getErrorDetail(error, TestPlatformError::getErrorMessage);
        return this;
    }

    public TestPlatformErrorResponse withCode(int code) {
        this.code = code;
        return this;
    }

    public TestPlatformErrorResponse withCode(TestPlatformError error) {
        this.code = TestPlatformError.getErrorDetail(error, TestPlatformError::getErrorCode);
        return this;
    }

    public TestPlatformErrorResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    public TestPlatformErrorResponse withMessage(TestPlatformError error) {
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
