package com.service.discovery.config;

import com.service.core.constants.ApiConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static com.service.core.constants.ApiConstants.*;

@Configuration
@EnableWebFluxSecurity
public class SecuriyFilterConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        return http
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwkSetUri("http://127.0.0.1:6000/login")))
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers( API_BASE+API_ACCOUNT_MANAGE +"/**").hasAuthority("account-server.access")
                        .pathMatchers(API_BASE+API_USER_MANAGE+"/**").hasAuthority("user-server.access")
                        .anyExchange().authenticated()
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }



}
