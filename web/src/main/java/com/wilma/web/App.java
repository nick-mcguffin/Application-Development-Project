package com.wilma.web;

import com.wilma.service.mail.Mailer;
import com.wilma.service.mail.MailerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.wilma.*"})
@EntityScan(basePackages = {"com.wilma.*"})
@EnableJpaRepositories(basePackages = {"com.wilma.*"})

public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
    
    @Bean
    public Mailer mailer(){
        return new MailerImpl();
    }
}
