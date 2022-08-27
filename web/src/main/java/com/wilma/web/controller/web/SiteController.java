package com.wilma.web.controller.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller (non-rest) for the public-facing (unsecured) web pages
 */
@Controller
public class SiteController {
    /*
    Todo: Change endpoint methods as web pages are created
        E.g.
        @GetMapping("/")
        private String home(Model model) {
            model.addAllAttributes(Map.of(
                    "title", "Home",
                    "page", "home"
            ));
            return "/index";
        }
     */
    @RequestMapping
    public ResponseEntity<?> home(){
        return ResponseEntity.ok("Home");
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

    @RequestMapping("/login")
    public ResponseEntity<?> login(){
        return ResponseEntity.ok("Login");
    }
}
