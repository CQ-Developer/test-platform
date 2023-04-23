package org.huhu.test.platform.converter.test;

import cn.hutool.core.util.ObjectUtil;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import static org.junit.jupiter.params.converter.DefaultArgumentConverter.INSTANCE;

/**
 * 测试平参数化单元测试类型转换器
 * 将值为 {@code null} 的 {@link String} 类型转换为 {@code null}
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public class NullValueArgumentConverter extends SimpleArgumentConverter {

    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
        if (ObjectUtil.equal("null", source)) {
            return null;
        }
        return INSTANCE.convert(source, targetType);
    }
}
