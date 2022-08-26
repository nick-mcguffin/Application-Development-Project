
package com.wilma.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@ComponentScan
@Profile("prod")
public class SecurityConfigPROD {

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http//.httpBasic().and()

                .authorizeRequests()
                .antMatchers("/educators/**").hasRole("ADMIN")
                .antMatchers("/students/**").hasAnyRole("STUDENT", "ADMIN")
                .antMatchers("/", "/h2/**").permitAll().and()

                .formLogin()//.loginPage("/login")
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler()).and()

                .logout().logoutSuccessUrl("/")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID");

        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();
    }

}
