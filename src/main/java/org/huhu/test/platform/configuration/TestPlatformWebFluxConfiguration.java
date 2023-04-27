package org.huhu.test.platform.configuration;

import cn.hutool.core.util.StrUtil;
import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import org.huhu.test.platform.constant.TestPlatformUserModifyPath;
import org.huhu.test.platform.constant.TestPlatformVariableScope;
import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.stream.Stream;

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
        registry.addConverter(String.class, TestPlatformRoleLevel.class, this::roleLevelDeserialize);
        registry.addConverter(String.class, TestPlatformVariableScope.class, this::variableScopeDeserialize);
        registry.addConverter(String.class, TestPlatformUserModifyPath.class, this::userModifyDeserialize);
    }

    private TestPlatformUserModifyPath userModifyDeserialize(String path) {
        return Stream.of(TestPlatformUserModifyPath.values())
                     .filter(i -> StrUtil.equalsIgnoreCase(i.name(), path))
                     .findAny()
                     .orElseThrow(() -> new ClientTestPlatformException("client user modify path invalid"));
    }

    private TestPlatformRoleLevel roleLevelDeserialize(String roleLevel) {
        if (StrUtil.isNumeric(roleLevel)) {
            return TestPlatformRoleLevel.deserialize(Integer.parseInt(roleLevel));
        }
        throw new ClientTestPlatformException("client role level invalid");
    }

    private TestPlatformVariableScope variableScopeDeserialize(String variableScope) {
        if (StrUtil.isNumeric(variableScope)) {
            return TestPlatformVariableScope.deserialize(Integer.parseInt(variableScope));
        }
        throw new ClientTestPlatformException("client variable scope invalid");
    }

}
