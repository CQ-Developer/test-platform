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
public enum TestPlatformCaseAuthType {

    /**
     * http basic 认证方式
     */
    HTTP_BASIC(1);

    /**
     * 认证类型
     */
    final int type;

    /**
     * 构造器函数
     */
    TestPlatformCaseAuthType(int type) {
        this.type = type;
    }

    /**
     * 获取认证类型
     */
    @JsonValue
    public int getType() {
        return type;
    }

    /**
     * 对客户端发送的http请求进行反序列化
     *
     * @param type 认证类型
     */
    @JsonCreator
    public static TestPlatformCaseAuthType deserialize(int type) {
        for (TestPlatformCaseAuthType testPlatformCaseAuthType : values()) {
            if (testPlatformCaseAuthType.getType() == type) {
                return testPlatformCaseAuthType;
            }
        }
        throw new ClientTestPlatformException("client case auth type invalid");
    }

}
