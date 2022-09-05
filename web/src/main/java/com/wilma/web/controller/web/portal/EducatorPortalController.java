package com.wilma.web.controller.web.portal;

import com.wilma.config.web.UserConfiguration;
import com.wilma.entity.Frequency;
import com.wilma.entity.PayType;
import com.wilma.entity.positions.Job;

import com.wilma.entity.positions.Placement;
import com.wilma.entity.users.Partner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/educator")
public class EducatorPortalController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "dashboard",
                "menuElements", UserConfiguration.educatorMenuElements
        ));
        return "/educator/dashboard";
    }
    /*
    Todo:
        - ADP-74: Jobs & Placement Management
        - ADP-72: Forum
        - ADP-75: Forum Management
        - ADP-81: Expressions Of Interest
        - ADP-73: Profile
     */
}
