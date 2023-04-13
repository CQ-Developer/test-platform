package org.huhu.test.platform.exception;

import org.huhu.test.platform.constant.TestPlatformErrorCode;

import static org.huhu.test.platform.constant.TestPlatformErrorCode.SERVICE_ERROR;

/**
 * 测试平台第三方服务错误
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public class ServiceTestPlatformException extends TestPlatformException {

    public ServiceTestPlatformException() {}

    public ServiceTestPlatformException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public TestPlatformErrorCode getTestPlatformError() {
        return SERVICE_ERROR;
    }

}
