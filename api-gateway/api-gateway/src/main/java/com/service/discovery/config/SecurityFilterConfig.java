package com.service.discovery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static com.service.core.constants.ApiConstants.*;

@Configuration
@EnableWebFluxSecurity
public class SecurityFilterConfig {

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        String jwkSetEndpoint = "http://127.0.0.1:6000/oauth2/jwks";
        return NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetEndpoint)
                .build();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtDecoder(jwtDecoder())))
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(API_BASE+API_USER_MANAGE+ "/sign-up",API_BASE+API_USER_MANAGE+"/login").permitAll()
                        .pathMatchers( API_BASE+API_ACCOUNT_MANAGE +"/**").hasAuthority("api.access")
                        .pathMatchers(API_BASE+API_USER_MANAGE+"/**").hasAuthority("api.access")
                        .anyExchange().authenticated()
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }



}
