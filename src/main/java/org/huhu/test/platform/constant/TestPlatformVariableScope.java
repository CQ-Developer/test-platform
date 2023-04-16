package org.huhu.test.platform.constant;

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
     * 测试零时变量
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
    GROUP(3);

    /**
     * 变量域
     */
    final int scope;

    TestPlatformVariableScope(int scope) {
        this.scope = scope;
    }

    public int getScope() {
        return scope;
    }

}
