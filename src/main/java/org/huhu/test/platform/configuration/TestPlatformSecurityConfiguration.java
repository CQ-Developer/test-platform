package org.huhu.test.platform.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterChainProxy;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion.$2A;

/**
 * 安全配置
 *
 * @see WebFilterChainProxy
 */
@Configuration(proxyBeanMethods = false)
public class TestPlatformSecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity
                .httpBasic()
                .and()
                .authorizeExchange()
                .pathMatchers("/actuator/**").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        secureRandom.setSeed(System.currentTimeMillis());
        return new BCryptPasswordEncoder($2A, 18, secureRandom);
    }

}
