package org.huhu.test.platform.converter;

import org.huhu.test.platform.constant.TestPlatformCaseAuthType;
import org.huhu.test.platform.util.ConvertUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ByteToTestPlatformCaseAuthTypeConverter implements Converter<Byte, TestPlatformCaseAuthType> {

    @Override
    public TestPlatformCaseAuthType convert(Byte source) {
        return ConvertUtils.toTestplatformCaseAuthType(source);
    }

}
