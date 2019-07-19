package com.rms.rms.common.config;

import com.rms.rms.common.config.properties.SecurityProperties;
import com.rms.rms.common.util.MyJwtUtil;
import com.rms.rms.controller.filter.AppCorsFilter;
import com.rms.rms.controller.filter.SecurityTokenFilter;
import com.rms.rms.service.AuthenService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * homertruong
 *
 * Use SecurityTokenFilter to implement token-based authentication using JWT
 */

@Configuration
/* this ConditionalOnProperty config is used to disable security config, default enable*/
@ConditionalOnProperty(prefix = "security.web", name = "enabled", matchIfMissing = true)
@EnableWebSecurity
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyJwtUtil myJwtUtil;

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenService authenService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // configure security token filter
        SecurityTokenFilter taf = new SecurityTokenFilter(myJwtUtil, authenService, beanMapper);
        httpSecurity.addFilterBefore(taf, LogoutFilter.class);
        httpSecurity.csrf().disable();  // disable CSRF (Cross-Site Request Forgery) because security token is used

        // configure request filter
        httpSecurity.authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/v1/health").permitAll()
                .antMatchers("/v1/domains/**").permitAll()
                .antMatchers("/v1/reset_password").permitAll()
                .antMatchers("/v1/subscribers/search").permitAll()
                .antMatchers("/v1/affiliates/sign_up").permitAll()
                .antMatchers("/v1/aws-s3-pre-signed-url").permitAll()
                .antMatchers("/v1/subs_infusion_configs/code/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/webjars/springfox-swagger-ui/*").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/csrf/**").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated();      // check Authentication object in Spring Security Context

        // configure CORS (Cross-Origin Resource Sharing)
        SecurityProperties.CorsProperties corsProperties = securityProperties.getCors();
        if (corsProperties.getIsEnabled()) {
            List<String> allowedOrigins = parse(corsProperties.getAllowedOrigins());
            List<String> allowedHeaders = parse(corsProperties.getAllowedHeaders());
            List<String> allowedMethods = parse(corsProperties.getAllowedMethods());
            List<String> allowedExposeHeaders = parse(corsProperties.getAllowedExposedHeaders());
            Boolean isAllowedCredentials = corsProperties.getIsAllowedCredentials();

            AppCorsFilter appCorsFilter = new AppCorsFilter(
                isAllowedCredentials == null ? Boolean.TRUE : isAllowedCredentials,
                allowedOrigins,
                allowedHeaders,
                allowedMethods,
                allowedExposeHeaders
            );
            httpSecurity.addFilterBefore(appCorsFilter, SecurityTokenFilter.class);
        }
    }


    // Utilities
    private List<String> parse(String str) {
        List<String> result = new ArrayList<>();

        if (StringUtils.isBlank(str)) {
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
