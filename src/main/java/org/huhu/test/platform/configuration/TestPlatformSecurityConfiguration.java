package org.huhu.test.platform.configuration;

import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.huhu.test.platform.constant.TestPlatformRoleLevel.ADMIN;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.DEV;
import static org.huhu.test.platform.constant.TestPlatformRoleLevel.ROLE_PRE;
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

    /**
     * 这种配置方式只是说明 {@link RoleHierarchy} 在Reactive环境中使用方式,
     * 可以使用普通的方式进行配置
     */
    @Bean
    @Order(0)
    public SecurityWebFilterChain actuatorSecurityWebFilterChain(
            ServerHttpSecurity serverHttpSecurity, RoleHierarchy roleHierarchy) {
        return serverHttpSecurity
                .securityMatcher(EndpointRequest.toAnyEndpoint())
                .httpBasic()
                .and()
                .authorizeExchange()
                .anyExchange().access((authentication, context) -> authentication
                        .map(Authentication::getAuthorities)
                        .map(roleHierarchy::getReachableGrantedAuthorities)
                        .flatMapMany(Flux::fromIterable)
                        .map(GrantedAuthority::getAuthority)
                        .any(DEV.getRoleName()::equals)
                        .map(AuthorizationDecision::new))
                .and()
                .build();
    }

    @Bean
    @Order(1)
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity
                .httpBasic()
                .and()
                .authorizeExchange()
                .pathMatchers(GET, "/user", "/user/role").authenticated()
                .pathMatchers("/user/profile", "/user/profile/**").authenticated()
                .pathMatchers("/user/**").hasRole(ADMIN.name())
                .anyExchange().authenticated()
                .and()
                // todo csrf开发阶段关闭
                .csrf().disable()
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
        var roleHierarchy = new RoleHierarchyImpl();
        var roleLevels = Stream
                .of(TestPlatformRoleLevel.values())
                .sorted(Comparator.comparing(TestPlatformRoleLevel::getLevel, Integer::compare).reversed())
                .toArray(TestPlatformRoleLevel[]::new);
        var entry = new ArrayList<String>();
        for (int i = 0; i < roleLevels.length - 1; i++) {
            var high = ROLE_PRE + roleLevels[i].name();
            var low = ROLE_PRE + roleLevels[i + 1].name();
            entry.add(String.join(" > ", high, low));
        }
        var roleEntry = String.join(System.lineSeparator(), entry.toArray(String[]::new));
        roleHierarchy.setHierarchy(roleEntry);
        return roleHierarchy;
    }

}
