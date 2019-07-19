package com.hoang.uma.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * homertruong
 */

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true)
public class GlobalMethodSecurityConfig
        extends GlobalMethodSecurityConfiguration {
}
