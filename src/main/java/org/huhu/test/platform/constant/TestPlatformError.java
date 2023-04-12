package org.huhu.test.platform.constant;

import java.util.function.Function;

public enum TestPlatformError {

    CLIENT_ERROR(1000, "client request error"),
    CLIENT_REQUEST_PARAMETER_INVALID(1001, "request parameter invalid"),
    CLIENT_REQUEST_USERNAME_INVALID(1002, "request username invalid"),

    THIRD_PARTY_SERVICE_ERROR(2000, "third party service error"),

    SERVER_ERROR(3000, "server occurred an unknown error");

    final int errorCode;

    final String errorMessage;

    TestPlatformError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static <T> T getErrorDetail(TestPlatformError item, Function<TestPlatformError, T> getDetail) {
        return getDetail.apply(item);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
