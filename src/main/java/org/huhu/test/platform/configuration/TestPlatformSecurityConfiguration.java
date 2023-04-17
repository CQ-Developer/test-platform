package org.huhu.test.platform.configuration;

import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.huhu.test.platform.constant.TestPlatformRoleLevel.ADMIN;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.DEV;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion.$2A;

/**
 * 测试平台安全配置
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
@Configuration(proxyBeanMethods = false)
public class TestPlatformSecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity
                .httpBasic()
                .and()
                .authorizeExchange()
                .pathMatchers(GET, "/user/role").authenticated()
                .pathMatchers("/user/**").hasRole(ADMIN.name())
                .anyExchange().authenticated()
                .and()
                // todo csrf开发阶段关闭
                .csrf().disable()
                .build();
    }

    @Bean
    public SecurityWebFilterChain actuatorSecurityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity
                .securityMatcher(EndpointRequest.toAnyEndpoint())
                .authorizeExchange()
                .anyExchange().hasAnyRole(DEV.name(), ADMIN.name())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() throws NoSuchAlgorithmException {
        var secureRandom = SecureRandom.getInstanceStrong();
        secureRandom.setSeed(System.currentTimeMillis());
        return new BCryptPasswordEncoder($2A, 8, secureRandom);
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        String role = Arrays.stream(TestPlatformRoleLevel.values())
                               .map(TestPlatformRoleLevel::name)
                               .map("ROLE_"::concat)
                               .collect(Collectors.joining(" > "));
        var roleHierarchy = new RoleHierarchyImpl();
        // todo 实现权限级别开发
        return roleHierarchy;
    }

}
