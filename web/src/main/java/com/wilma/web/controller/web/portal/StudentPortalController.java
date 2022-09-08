package com.wilma.web.controller.web.portal;

import com.wilma.config.web.UserConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentPortalController {
    /*
    Todo:
        - ADP-65: Dashboard
        - ADP-66: Jobs & Placements
        - ADP-67: Forum
        - ADP-68: Resume Management
        - ADP-69: Profile
     */

    @GetMapping("/studentProfile")
    public String studentProfile(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "profile",
                "menuElements", UserConfiguration.studentMenuElements
        ));
        return "/student/studentProfile";
    }
}
