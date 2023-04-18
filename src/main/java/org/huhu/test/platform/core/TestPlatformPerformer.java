package org.huhu.test.platform.core;

/**
 * 测试平台执行接口
 * 实现该接口意味着该对象是一个可执行对象
 *
 * @param <P> 参数类型
 * @param <R> 结果类型
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
@FunctionalInterface
public interface TestPlatformPerformer<P, R> {

    /**
     * 执行
     *
     * @param p 执行参数
     *
     * @return 执行结果
     */
    R doPerform(P p);

}
