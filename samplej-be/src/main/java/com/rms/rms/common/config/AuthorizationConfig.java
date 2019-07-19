package com.rms.rms.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * homertruong
 *
 * Use method-level authorization on Roles
 */

@Configuration
@ConditionalOnProperty(prefix = "security.global", name = "enabled", matchIfMissing = true)
@EnableGlobalMethodSecurity(securedEnabled=true)
public class AuthorizationConfig extends GlobalMethodSecurityConfiguration {
}
