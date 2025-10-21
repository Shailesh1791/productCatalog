package com.service.productCatalog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jwt.*;


@Configuration
public class JwtConfig {

    @Bean
    public JwtDecoder jwtDecoder(@Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}") String jwkSetUri) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();

        jwtDecoder.setJwtValidator(token -> {
            String tokenIss = token.getClaimAsString("iss");
            if ("accounts.google.com".equals(tokenIss) ||
                    "https://accounts.google.com".equals(tokenIss) ||
                    "https://accounts.google.com/".equals(tokenIss)) {
                return OAuth2TokenValidatorResult.success();
            }
            return OAuth2TokenValidatorResult.failure(
                    new OAuth2Error("invalid_token", "Invalid issuer: " + tokenIss, null)
            );
        });

        return jwtDecoder;
    }


}
