package com.webfilminfo.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        // Password encoder, to Spring Security encode user password
        return new BCryptPasswordEncoder();
    }
}
