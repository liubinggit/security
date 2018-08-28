package com.liubing.security.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.liubing.security.core.properties.SecurityPeoperties;

@Configuration
@EnableConfigurationProperties(SecurityPeoperties.class)
public class SecurityCoreConfig {

}
