package org.huhu.test.platform.converter;

import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import org.huhu.test.platform.util.ConvertUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * 将 {@link Byte} 转换为 {@link TestPlatformRoleLevel}
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.model.table.TestPlatformUserRole
 * @since 0.0.1
 */
public class ByteToTestPlatformRoleLevelConverter implements Converter<Byte, TestPlatformRoleLevel> {

    @Override
    public TestPlatformRoleLevel convert(Byte source) {
        return ConvertUtils.from(source);
    }

}
