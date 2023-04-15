package org.huhu.test.platform.converter;

import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import org.springframework.core.convert.converter.Converter;

/**
 * 将 {@link TestPlatformRoleLevel} 转换为 {@link Byte}
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.model.table.TestPlatformUserRole
 * @since 0.0.1
 */
public class TestPlatformRoleLevelToByteConverter implements Converter<TestPlatformRoleLevel, Byte> {

    @Override
    public Byte convert(TestPlatformRoleLevel source) {
        return source.getLevel();
    }

}
