package org.huhu.test.platform.constant;

/**
 * 测试平台用户变更请求路径
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public enum TestPlatformUserModifyPath {

    /**
     * 刷新用户过期时间
     */
    RENEW,

    /**
     * 刷新用户密码过期时间
     */
    VERIFY,

    /**
     * 启用用户
     */
    ENABLE,

    /**
     * 禁用用户
     */
    DISABLE,

    /**
     * 锁定用户
     */
    LOCK,

    /**
     * 解锁用户
     */
    UNLOCK

}
