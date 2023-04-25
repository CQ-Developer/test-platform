package org.huhu.test.platform.util;

import cn.hutool.core.collection.CollectionUtil;
import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import reactor.util.function.Tuple2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 集合工具类
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public final class CollectionUtils {

    /**
     * 私有构造器
     */
    private CollectionUtils() {}

    /**
     * 查找出两个集合的差集
     *
     * @param tuple2 用户当前所有的角色 期望添加的新角色
     *
     * @return 返回未添加的角色
     */
    public static Set<TestPlatformRoleLevel> subtract(Tuple2<List<TestPlatformRoleLevel>, Set<TestPlatformRoleLevel>> tuple2) {
        var result = CollectionUtil.subtract(tuple2.getT2(), tuple2.getT1());
        return new HashSet<>(result);
    }

}
