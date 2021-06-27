package com.kachunchan.academicrecordbook.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
    protected void configure(HttpSecurity http) throws Exception {

//                authorizeRequests().anyRequest().permitAll()
        http
                .authorizeRequests().antMatchers("/h2","/h2/**").permitAll()
                .anyRequest().authenticated()
                .and().headers().frameOptions().disable()
                .and().csrf().disable().httpBasic();

    }
}
