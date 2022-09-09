package com.wilma.web.controller.web.portal;

import com.wilma.config.web.UserConfiguration;
import com.wilma.entity.users.Resume;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
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
    @GetMapping("/resumeManagement")
    public String resumeManagement(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "Resume Management",
                "menuElements", UserConfiguration.studentMenuElements,
                "studentResumes", List.of(
                        new Resume("Resume 1", "pdf"), (new Resume("Resume 2", "pdf"))
                        ,(new Resume("Resume 3", "pdf")),(new Resume("Resume 4", "pdf"))
                )
        ));
        return "/student/resumeManagement";
    }
}
