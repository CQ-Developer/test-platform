package org.huhu.test.platform.converter;

import org.huhu.test.platform.constant.TestPlatformVariableScope;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * 将 {@link TestPlatformVariableScope} 转换为 {@link Byte}
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.model.table.TestPlatformVariable
 * @since 0.0.1
 */
@WritingConverter
public class TestPlatformVariableScopeToByteConverter implements Converter<TestPlatformVariableScope, Byte> {

    @Override
    public Byte convert(TestPlatformVariableScope source) {
        return (byte) source.getScope();
    }

}
