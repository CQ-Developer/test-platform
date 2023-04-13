package org.huhu.test.platform.constant;

/**
 * 测试平台错误码枚举类
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public enum TestPlatformError {

    /**
     * 客户端请求错误
     */
    CLIENT_ERROR(1000, "client request error"),

    /**
     * 客户端请求参数错误
     */
    CLIENT_REQUEST_PARAMETER_INVALID(1001, "request parameter invalid"),

    /**
     * 客户端请求的用户名非法
     */
    CLIENT_REQUEST_USERNAME_INVALID(1002, "request username invalid"),

    /**
     * 第三方服务错误
     */
    THIRD_PARTY_SERVICE_ERROR(2000, "third party service error"),

    /**
     * 服务端错误
     */
    SERVER_ERROR(3000, "server occurred an unknown error");

    /**
     * 错误码
     */
    final int errorCode;

    /**
     * 错误信息
     */
    final String errorMessage;

    TestPlatformError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * 获取错误码
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 获取错误信息
     */
    public String getErrorMessage() {
        return errorMessage;
    }

}
