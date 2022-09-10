package com.wilma.web.controller.web.portal;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wilma.config.web.UserConfiguration;

@Controller
@RequestMapping ("/student")
public class StudentPortalController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "dashboard",
                "menuElements", UserConfiguration.studentMenuElements
        ));
        return "/student/dashboard";
    }
    
    /*
    Todo:
        - ADP-65: Dashboard
        - ADP-66: Jobs & Placements
        - ADP-67: Forum
        - ADP-68: Resume Management
        - ADP-69: Profile
     */
}
