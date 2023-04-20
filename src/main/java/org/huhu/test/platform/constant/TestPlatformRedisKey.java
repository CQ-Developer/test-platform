package org.huhu.test.platform.constant;

import static java.util.Locale.US;

/**
 * 测试平台Redis键枚举类
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public enum TestPlatformRedisKey {

    /**
     * 用户当前激活环境
     */
    USER_PROFILE_ACTIVE("test:platform:user:profile:active:%s");

    /**
     * Redis键模板
     */
    final String keyTemplate;

    /**
     * 构造函数
     */
    TestPlatformRedisKey(String keyTemplate) {
        this.keyTemplate = keyTemplate;
    }

    /**
     * 获取Redis键
     *
     * @param args 模板参数
     */
    public String getKey(Object... args) {
        return String.format(US, keyTemplate, args);
    }

}
