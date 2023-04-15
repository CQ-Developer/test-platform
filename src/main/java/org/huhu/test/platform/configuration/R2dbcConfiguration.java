package org.huhu.test.platform.configuration;

import org.huhu.test.platform.converter.ByteToTestPlatformRoleLevelConverter;
import org.huhu.test.platform.converter.TestPlatformRoleLevelToByteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.r2dbc.core.DatabaseClient;

import java.util.List;

@Configuration
public class R2dbcConfiguration {

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(DatabaseClient databaseClient) {
        var converters = List.of(
                new TestPlatformRoleLevelToByteConverter(), new ByteToTestPlatformRoleLevelConverter());
        var dialect = DialectResolver.getDialect(databaseClient.getConnectionFactory());
        return R2dbcCustomConversions.of(dialect, converters);
    }

}
