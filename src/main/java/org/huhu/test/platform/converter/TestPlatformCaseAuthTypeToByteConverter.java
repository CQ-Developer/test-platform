package org.huhu.test.platform.converter;

import org.huhu.test.platform.constant.TestPlatformCaseAuthType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class TestPlatformCaseAuthTypeToByteConverter implements Converter<TestPlatformCaseAuthType, Byte> {

    @Override
    public Byte convert(TestPlatformCaseAuthType source) {
        return (byte) source.getType();
    }

}
