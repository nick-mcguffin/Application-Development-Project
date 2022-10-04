package com.wilma.config.security;

import com.wilma.service.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring security configuration used while in the DEV profile.
 */
@Configuration
@ComponentScan
@Profile("dev")
public class SecurityConfigDEV {

    /**
     * Instantiates an instance of a custom user details service which can then be auto-wired throughout the application
     *
     * @return A custom user details service implementation
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    /**
     * Instantiates an instance of the chosen password encoder which can then be auto-wired throughout the application
     *
     * @return The chosen password encoder implementation
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines security filters to be implemented by the application
     *
     * @param http The http security builder
     * @return A security filter chain containing the defined filers
     * @throws Exception Multiple exception possibilities
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll();

        //Disable cross site referencing and header frames for H2 database
        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();
    }
}
