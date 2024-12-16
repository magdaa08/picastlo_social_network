package com.picastlo.pipelineservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@Configuration
class CorsConfig {
    @Bean
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()
        config.addAllowedOrigin("http://localhost:8080") // Allow API Gateway
        config.addAllowedMethod("*") // Allow all HTTP methods (GET, POST, etc.)
        config.addAllowedHeader("*") // Allow all headers
        config.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/v3/api-docs/**", config) // Apply to /v3/api-docs

        return CorsFilter(source)
    }
}
