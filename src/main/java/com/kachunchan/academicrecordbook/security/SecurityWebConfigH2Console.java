package com.kachunchan.academicrecordbook.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Profile("EnableH2Console")
@Configuration
@EnableWebSecurity
public class SecurityWebConfigH2Console extends SecurityWebConfig {

    public SecurityWebConfigH2Console(UserDetailsService userDetailsService) {
        super(userDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toH2Console());
    }
}
