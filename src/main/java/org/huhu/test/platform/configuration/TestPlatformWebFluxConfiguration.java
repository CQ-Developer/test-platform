package org.huhu.test.platform.configuration;

import cn.hutool.core.util.StrUtil;
import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * 测试平台请求参数类型转换配置
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
@Configuration
public class TestPlatformWebFluxConfiguration implements WebFluxConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, TestPlatformRoleLevel.class, this::deserialize);
    }

    private TestPlatformRoleLevel deserialize(String roleLevel) {
        if (StrUtil.isNumeric(roleLevel)) {
            return TestPlatformRoleLevel.deserialize(Integer.parseInt(roleLevel));
        }
        throw new ClientTestPlatformException("client role level invalid");
    }

}
