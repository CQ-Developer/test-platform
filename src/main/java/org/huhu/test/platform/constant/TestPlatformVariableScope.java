package org.huhu.test.platform.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.huhu.test.platform.model.table.TestPlatformVariable;

/**
 * 测试平台变量作用域
 *
 * @author 18551681083@163.com
 * @see TestPlatformVariable
 * @since 0.0.1
 */
public enum TestPlatformVariableScope {


    /**
     * 测试临时变量
     */
    TMP(0),

    /**
     * 测试用例变量
     */
    CASE(1),

    /**
     * 测试套件变量
     */
    SUITE(2),

    /**
     * 测试集合变量
     */
    GROUP(3),

    /**
     * 全局变量零时变量
     */
    GLOBAL(4);

    /**
     * 变量域
     */
    final int scope;

    /**
     * 构造器函数
     */
    TestPlatformVariableScope(int scope) {
        this.scope = scope;
    }

    /**
     * 获取变量作用域
     */
    @JsonValue
    public int getScope() {
        return scope;
    }

    @JsonCreator
    public static TestPlatformVariableScope deserialize(int scope) {
        for (TestPlatformVariableScope testPlatformVariableScope : values()) {
            if (testPlatformVariableScope.getScope() == scope) {
                return testPlatformVariableScope;
            }
        }
        throw new ClientTestPlatformException("client variable scope invalid");
    }
}
