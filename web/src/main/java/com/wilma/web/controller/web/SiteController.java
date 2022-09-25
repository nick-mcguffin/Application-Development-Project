package com.wilma.web.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wilma.entity.contact.ContactForm;
import com.wilma.service.mail.Mailer;

import java.util.List;
import java.util.Map;

/**
 * Controller (non-rest) for the public-facing (unsecured) web pages
 */
@Controller
public class SiteController {

    @Autowired
    protected Mailer mailer;

    @GetMapping("/")
    public String home(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "Home"
        ));
        return "/index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAllAttributes(Map.of(
            "currentPage", "Register",
                "userOptions", List.of("Select...", "Educator", "Partner", "Student")));
        return "/register";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAllAttributes(Map.of(
            "currentPage", "About Us"));
        return "/about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAllAttributes(Map.of(
            "currentPage", "Contact Us",
            "contactForm", new ContactForm()));//Need to pass a new blank contact form so thymeleaf knows the shape we're working with
        return "/contact";
    }

    @PostMapping("/submit")
    public String contactSubmit(@ModelAttribute ContactForm contactForm, Model model) {

        model.addAllAttributes(Map.of(
            "currentPage", "Contact Us"));

        String mailerMessage = "From: " + contactForm.getName() + "\n\n" + "Contact Email: " + contactForm.getEmail() + "\n\n" + "Message: " + contactForm.getDetails();
        
        mailer.sendEmail("nicholas.mcguffin@gmail.com", "WILMA: Contact Us Request Received",  mailerMessage);

        return "/contact";
    }
        //autowiring mailing interface
        //call send email function to => 
    // }
    
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("currentPage", "Login");
        return "/login";
    }


}

