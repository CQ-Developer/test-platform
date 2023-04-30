package org.huhu.test.platform.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.huhu.test.platform.exception.ClientTestPlatformException;

/**
 * 测试平台请求方法
 *
 * @author 18551681083@163.com
 * @since 0.0.2
 */
public enum TestPlatformCaseMethod {

    /**
     * GET请求
     *
     * @see org.springframework.http.HttpMethod#GET
     */
    GET(1),

    /**
     * POST请求
     *
     * @see org.springframework.http.HttpMethod#POST
     */
    POST(2),

    /**
     * PUT请求
     *
     * @see org.springframework.http.HttpMethod#PUT
     */
    PUT(3),

    /**
     * DELETE请求
     *
     * @see org.springframework.http.HttpMethod#DELETE
     */
    DELETE(4);

    /**
     * 请求方法
     */
    final int method;

    /**
     * 构造器函数
     */
    TestPlatformCaseMethod(int method) {
        this.method = method;
    }

    /**
     * 获取请求方法
     */
    @JsonValue
    public int getMethod() {
        return method;
    }

    /**
     * 对客户端发送的http请求参数进行凡序列化
     *
     * @param method 客户端请求方法
     */
    @JsonCreator
    public static TestPlatformCaseMethod deserialize(int method) {
        for (TestPlatformCaseMethod testPlatformCaseMethod : values()) {
            if (testPlatformCaseMethod.getMethod() == method) {
                return testPlatformCaseMethod;
            }
        }
        throw new ClientTestPlatformException("client variable scope invalid");
    }

}
