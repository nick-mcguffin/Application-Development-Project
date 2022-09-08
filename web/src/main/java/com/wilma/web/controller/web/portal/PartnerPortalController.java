package com.wilma.web.controller.web.portal;

import com.wilma.config.web.UserConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/partner")
public class PartnerPortalController {
    /*
    Todo:
        - ADP-76: Dashboard
        - ADP-77: Jobs & Placements
        - ADP-78: Forum
        - ADP-79: Job & Placement Management
        - ADP-80: Profile
        - ADP-82: Expressions Of Interest
     */

    @GetMapping("/partnerProfile")
    public String partnerProfile(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "Profile",
                "menuElements", UserConfiguration.partnerMenuElements
        ));
        return "/partner/partnerProfile";
    }
}
