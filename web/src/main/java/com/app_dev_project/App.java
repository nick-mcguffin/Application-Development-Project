package com.app_dev_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.app_dev_project.*"})
@EntityScan(basePackages = {"com.app_dev_project.*"})
@EnableJpaRepositories(basePackages = {"com.app_dev_project.*"})

public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
    
}
