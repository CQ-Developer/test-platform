package org.huhu.test.platform.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterChainProxy;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion.$2A;

/**
 * 配置
 *
 * @see WebFilterChainProxy
 */
@Configuration(proxyBeanMethods = false)
public class AppSecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity.httpBasic(Customizer.withDefaults())
                                 .authorizeExchange(this::doAuthorizeExchange)
                                 .build();
    }

    private void doAuthorizeExchange(AuthorizeExchangeSpec authorizeExchangeSpec) {
        authorizeExchangeSpec
                .pathMatchers("/").hasRole("USER")
                .pathMatchers("/actuator/**").hasRole("ADMIN")
                .anyExchange().authenticated();
    }

    @Bean
    public MapReactiveUserDetailsService mapReactiveUserDetailsService() {
        UserDetails userDetails = User.withUsername("root")
                                      .password("$2a$18$XDlE9fmniuAZ9QezyUcA9uWJblMWOlJwEHcxXGd2TaJctterMipBu")
                                      .roles("ADMIN")
                                      .build();
        return new MapReactiveUserDetailsService(userDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        secureRandom.setSeed(System.currentTimeMillis());
        return new BCryptPasswordEncoder($2A, 18, secureRandom);
    }

}
