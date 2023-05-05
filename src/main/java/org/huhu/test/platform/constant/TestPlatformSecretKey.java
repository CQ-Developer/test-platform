package org.huhu.test.platform.constant;

/**
 * 测试平台密钥枚举类
 *
 * @author 18551681083@163.com
 * @since 0.0.2
 */
public enum TestPlatformSecretKey {

    /**
     * 采用AES算法128位长度的密钥
     * 使用16进制保存的字符串类型
     */
    AES_128("b54dab8e6c04cccfe4e89a8b8dd54494");

    /**
     * 16进制密钥
     */
    final String key;

    /**
     * 构造器函数
     */
    TestPlatformSecretKey(String key) {
        this.key = key;
    }

    /**
     * 获取16进制的密钥
     */
    public String getKey() {
        return key;
    }

}
