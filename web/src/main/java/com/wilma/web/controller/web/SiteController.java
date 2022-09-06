package com.wilma.web.controller.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Controller (non-rest) for the public-facing (unsecured) web pages
 */
@Controller
public class SiteController {

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

    @RequestMapping("/about")
    public ResponseEntity<?> about() {
        return ResponseEntity.ok("About");
    }

    @RequestMapping("/contact")
    public ResponseEntity<?> contact() {
        return ResponseEntity.ok("Contact");
    }

    @RequestMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok("Login");
    }
}
