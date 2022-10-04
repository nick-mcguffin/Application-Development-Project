package com.wilma.config.web;

import com.wilma.config.handlers.APISecurityInterceptorPROD;
import com.wilma.repository.RemoteClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC configuration for the PROD profile
 */
@Profile("prod")
@Configuration
public class WebMvcConfigPROD implements WebMvcConfigurer {

    @Autowired
    RemoteClientRepository remoteClientRepository;

    /**
     * Registers custom interceptors
     * @param registry The interceptor registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new APISecurityInterceptorPROD(remoteClientRepository));
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
}
