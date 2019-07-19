package com.hoang.uma.controller.filter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * homertruong
 */
public class AppCorsFilter extends CorsFilter {

    public AppCorsFilter(Boolean isAllowedCredentials, List<String> allowedOrigins, List<String> allowedHeaders,
                         List<String> allowedMethods, List<String> allowedExposedHeaders)
    {
        super(configurationSource(isAllowedCredentials, allowedOrigins, allowedHeaders, allowedMethods, allowedExposedHeaders));
    }

    private static UrlBasedCorsConfigurationSource configurationSource(Boolean isAllowedCredentials,
        List<String> allowedOrigins, List<String> allowedHeaders, List<String> allowedMethods, List<String> allowedExposedHeaders)
    {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(isAllowedCredentials);
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedHeaders(allowedHeaders);
        config.setAllowedMethods(allowedMethods);
        config.setExposedHeaders(allowedExposedHeaders);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
