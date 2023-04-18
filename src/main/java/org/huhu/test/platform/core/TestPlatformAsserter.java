package org.huhu.test.platform.core;

/**
 * 测试平台断言接口
 * 实现该接口意味着需要对对象执行断言
 *
 * @param <T> 被对言对象类型
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
@FunctionalInterface
public interface TestPlatformAsserter<T> {

    /**
     * 断言
     *
     * @param t 被断言对象
     *
     * @return 断言结果
     */
    boolean doAssert(T t);

}
