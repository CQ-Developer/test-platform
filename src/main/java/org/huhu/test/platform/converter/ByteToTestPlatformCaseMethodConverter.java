package org.huhu.test.platform.converter;

import org.huhu.test.platform.constant.TestPlatformCaseMethod;
import org.huhu.test.platform.util.ConvertUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ByteToTestPlatformCaseMethodConverter implements Converter<Byte, TestPlatformCaseMethod> {

    @Override
    public TestPlatformCaseMethod convert(Byte source) {
        return ConvertUtils.toTestPlatformCaseMethod(source);
    }

}
