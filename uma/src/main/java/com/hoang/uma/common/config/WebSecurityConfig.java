package com.hoang.uma.common.config;

import com.hoang.uma.common.util.JwtUtils;
import com.hoang.uma.controller.filter.AppCorsFilter;
import com.hoang.uma.controller.filter.TokenAuthenticationFilter;
import com.hoang.uma.service.SecurityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * homertruong
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ModelMapper beanMapper;

    @Value("${cors.isAllowedCredentials}")
    private Boolean isAllowedCredentials;

    @Value("${cors.allowedOrigins}")
    private String allowedOriginsStr;

    @Value("${cors.allowedHeaders}")
    private String allowedHeadersStr;

    @Value("${cors.allowedMethods}")
    private String allowedMethodsStr;

    @Value("${cors.allowedExposedHeaders}")
    private String allowedExposedHeadersStr;

    @Autowired
    private SecurityService securityService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/img/**").permitAll()
                    .antMatchers("/js/**").permitAll()
                    .antMatchers("/favicon.ico").permitAll()
                    .antMatchers("/v1/health").permitAll()
                    .antMatchers("/").permitAll()
                    .anyRequest().authenticated();

        httpSecurity.csrf().disable();

        TokenAuthenticationFilter taf = new TokenAuthenticationFilter(jwtUtils, securityService, beanMapper);
        httpSecurity.addFilterBefore(taf, LogoutFilter.class);

        List<String> allowedOrigins = parse(allowedOriginsStr);
        List<String> allowedHeaders = parse(allowedHeadersStr);
        List<String> allowedMethods = parse(allowedMethodsStr);
        List<String> allowedExposeHeaders = parse(allowedExposedHeadersStr);

        AppCorsFilter appCorsFilter = new AppCorsFilter(isAllowedCredentials == null? Boolean.TRUE : isAllowedCredentials,
                                                        allowedOrigins, allowedHeaders, allowedMethods, allowedExposeHeaders);
        httpSecurity.addFilterBefore(appCorsFilter, TokenAuthenticationFilter.class);
    }

    private List<String> parse(String str) {
        List<String> result = new ArrayList<>();

        if (StringUtils.isEmpty(str)) {
            result.add("*");
        }
        else {
            String[] items = StringUtils.split(str, ",");
            if (items == null) {
                result.add("*");
            }
            else {
                Arrays.stream(items).forEach(item -> result.add(item));
            }
        }

        return result;
    }
}
