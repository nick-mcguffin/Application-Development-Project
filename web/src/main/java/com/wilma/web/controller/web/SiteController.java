package com.wilma.web.controller.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/register")
    public ResponseEntity<?> register(){
        return ResponseEntity.ok("Register");
    }

    @RequestMapping("/about")
    public ResponseEntity<?> about(){
        return ResponseEntity.ok("About");
    }

    @RequestMapping("/contact")
    public ResponseEntity<?> contact(){
        return ResponseEntity.ok("Contact");
    }
    @RequestMapping("/ForumCategories")
    public String ForumCategories() {
        return "Forum";
    }

    @RequestMapping("/login")
    public ResponseEntity<?> login(){
        return ResponseEntity.ok("Login");
    }


}
