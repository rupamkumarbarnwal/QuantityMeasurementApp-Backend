package com.quantitymeasurement.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.*;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(Arrays.asList(
                "http://localhost:3000",
                "https://quantity-measurement-app-frontend-peach.vercel.app",
                "https://quantity-measurement-app-frontend-*.vercel.app", // Allow all vercel preview branches
                "https://*.vercel.app" // Generic Vercel fallback
        ));

        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
