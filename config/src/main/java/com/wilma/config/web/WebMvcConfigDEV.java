package com.wilma.config.web;

import com.wilma.repository.RemoteClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC web configuration for the DEV profile
 */
@Profile("dev")
@Configuration
public class WebMvcConfigDEV implements WebMvcConfigurer {

    @Autowired
    RemoteClientRepository remoteClientRepository;

    //Login page not implemented yet
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/login").setViewName("login");
//    }
}
