package org.huhu.test.platform.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 测试平台 {@link org.springframework.data.redis.core.ReactiveRedisTemplate} 配置
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
@Configuration
public class TestPlatformRedisConfiguration {

    @Bean
    public ReactiveRedisTemplate<Object, Object> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        var serializationContext = RedisSerializationContext
                .newSerializationContext(RedisSerializer.string())
                .string(RedisSerializer.string())
                .key(RedisSerializer.json())
                .value(RedisSerializer.json())
                .hashKey(RedisSerializer.string())
                .hashValue(RedisSerializer.json())
                .build();
        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, serializationContext);
    }

}
