package org.huhu.test.platform.converter;

import org.huhu.test.platform.constant.TestPlatformCaseMethod;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class TestPlatformCaseMethodToByteConverter implements Converter<TestPlatformCaseMethod, Byte> {

    @Override
    public Byte convert(TestPlatformCaseMethod source) {
        return (byte) source.getMethod();
    }

}
