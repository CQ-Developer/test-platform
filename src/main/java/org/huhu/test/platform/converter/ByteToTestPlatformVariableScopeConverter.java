package org.huhu.test.platform.converter;

import org.huhu.test.platform.constant.TestPlatformVariableScope;
import org.huhu.test.platform.util.ConvertUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * 将 {@link Byte} 转换为 {@link TestPlatformVariableScope}
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.model.table.TestPlatformVariable
 * @since 0.0.1
 */
@ReadingConverter
public class ByteToTestPlatformVariableScopeConverter implements Converter<Byte, TestPlatformVariableScope> {

    @Override
    public TestPlatformVariableScope convert(Byte source) {
        return ConvertUtils.toTestPlatformVariableScope(source);
    }

}
