package org.huhu.test.platform.constant;

/**
 * 测试平台正则表达式
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public interface TestPlatFormRegexPattern {

    /**
     * 用户名正则表达式
     */
    String USERNAME = "^\\w{4,16}$";

    /**
     * 密码正则表达式
     */
    String PASSWORD = "^[\\w@#:-]{6,32}$";

    /**
     * 变量名正则表达式
     */
    String VARIABLE_NAME = "^\\w{1,32}$";

}
