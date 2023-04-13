package org.huhu.test.platform.constant;

/**
 * 测试平台错误码枚举类
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public enum TestPlatformErrorCode {

    /**
     * 客户端请求错误
     */
    CLIENT_ERROR(1000, "client error"),

    /**
     * 第三方服务错误
     */
    SERVICE_ERROR(2000, "service error"),

    /**
     * 服务端错误
     */
    SERVER_ERROR(3000, "server error");

    /**
     * 错误码
     */
    final int errorCode;

    /**
     * 错误信息
     */
    final String errorMessage;

    /**
     * 构造器
     *
     * @param errorCode 错误码
     * @param errorMessage 错误信息
     */
    TestPlatformErrorCode(int errorCode, String errorMessage) {
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
