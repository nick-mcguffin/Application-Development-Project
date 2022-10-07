
package com.wilma.config.security;

import com.wilma.config.handlers.CustomAccessDeniedHandler;
import com.wilma.config.handlers.LoginSuccessHandler;
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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Spring security configuration used while in the PROD profile.
 */
@Configuration
@ComponentScan
@Profile("prod")
public class SecurityConfigPROD {

    /**
     * @param http                  The http security builder
     * @param bCryptPasswordEncoder The chosen password encoder
     * @param userDetailsService    The chosen user details service
     * @return The custom authentication manager
     * @throws Exception Methods thrown by subsequent methods called from within
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    /**
     * Instantiating our BCrypt password encoder, so it can be auto-wired throughout the application
     *
     * @return The BCrypt password encoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new LoginSuccessHandler();
    }

    /**
     * Instantiating the access denied handler so that it can be aut-wired throughout the application
     *
     * @return The custom access denied handler
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
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
        http
                .authorizeRequests()
                .antMatchers("/educator/**").hasRole("ADMIN")
                .antMatchers("/partner/**").hasAnyRole("PARTNER", "ADMIN")
                .antMatchers("/student/**").hasAnyRole("STUDENT", "ADMIN")
                .antMatchers("/").permitAll().and()

                .formLogin().loginPage("/login")
                .successHandler(authenticationSuccessHandler())
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler()).and()

                .logout().logoutSuccessUrl("/")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID");

        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();
    }

}
