package org.huhu.test.platform.configuration;

import org.huhu.test.platform.converter.ByteToTestPlatformCaseMethodConverter;
import org.huhu.test.platform.converter.ByteToTestPlatformRoleLevelConverter;
import org.huhu.test.platform.converter.ByteToTestPlatformVariableScopeConverter;
import org.huhu.test.platform.converter.TestPlatformCaseMethodToByteConverter;
import org.huhu.test.platform.converter.TestPlatformRoleLevelToByteConverter;
import org.huhu.test.platform.converter.TestPlatformVariableScopeToByteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.List;

/**
 * 测试平台数据库操作类型转换配置
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
@Configuration
public class TestPlatformR2dbcConfiguration {

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(DatabaseClient databaseClient) {
        var converters = List.of(
                new TestPlatformRoleLevelToByteConverter(), new ByteToTestPlatformRoleLevelConverter(),
                new TestPlatformVariableScopeToByteConverter(), new ByteToTestPlatformVariableScopeConverter(),
                new TestPlatformCaseMethodToByteConverter(), new ByteToTestPlatformCaseMethodConverter());
        var dialect = DialectResolver.getDialect(databaseClient.getConnectionFactory());
        return R2dbcCustomConversions.of(dialect, converters);
    }

}
