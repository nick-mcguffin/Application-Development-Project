package com.wilma.web.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.wilma.entity.contact.ContactForm;
import com.wilma.entity.dto.UserDTO;
import com.wilma.service.UserRegistrationService;
import com.wilma.service.mail.Mailer;
import com.wilma.service.positions.ApplicationNotificationService;

import java.util.List;
import java.util.Map;

/**
 * Controller (non-rest) for the public-facing (unsecured) web pages
 */
@Controller
public class SiteController {

    @Autowired
    protected Mailer mailer;

    @Autowired
    protected ApplicationNotificationService applicationNotificationService;

    @Autowired 
    protected UserRegistrationService userRegistrationService;

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
            "userOptions", List.of("Select...", "Educator", "Partner", "Student"),
            "userDTO", new UserDTO()
        ));
        return "/register";
    }

    @PostMapping("/register")
    public RedirectView registerUser(@ModelAttribute UserDTO userDTO, Model model) {
        model.addAllAttributes(Map.of(
            "currentPage", "Register",
            "userOptions", List.of("Select...", "Educator", "Partner", "Student"),
            "userDTO", userDTO
            ));
            userRegistrationService.register(userDTO);
            return new RedirectView("/");
    }


    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken, Model model){
        model.addAllAttributes(Map.of(
            "currentPage", "Account Confirmation"));
        return userRegistrationService.confirmEmailVerification(confirmationToken);
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
            applicationNotificationService.sendContactUsEmail(contactForm);

        return "redirect:contact";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("currentPage", "Login");
        return "/login";
    }

}

