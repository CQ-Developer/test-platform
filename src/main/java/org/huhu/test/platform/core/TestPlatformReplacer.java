package org.huhu.test.platform.core;

/**
 * 测试平台变量替换接口
 * 实现该接口意味着需要对对象执行属性替换
 *
 * @param <T> 被替换对象类型
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
@FunctionalInterface
public interface TestPlatformReplacer<T> {

    /**
     * 替换
     *
     * @param t 被替换对象
     */
    void doReplace(T t);

}
