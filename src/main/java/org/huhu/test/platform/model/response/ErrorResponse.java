package org.huhu.test.platform.model.response;

import org.huhu.test.platform.constant.TestPlatformErrorCode;
import org.huhu.test.platform.exception.TestPlatformException;

/**
 * 测试平台错误
 *
 * @param code 错误码
 * @param message 错误消息
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.controller.TestPlatformErrorController
 * @since 0.0.1
 */
public record ErrorResponse(Integer code, String message) {

    public static ErrorResponse from(TestPlatformErrorCode error) {
        return new ErrorResponse(error.getErrorCode(), error.getErrorMessage());
    }

    public static ErrorResponse from(TestPlatformException exception) {
        return from(exception.getTestPlatformError());
    }

}
