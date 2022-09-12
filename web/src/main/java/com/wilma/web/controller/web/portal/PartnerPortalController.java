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
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping ("/partner")
public class PartnerPortalController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "dashboard",
                "menuElements", UserConfiguration.partnerMenuElements
        ));
        return "/partner/dashboard";
    }

    @GetMapping("/marketplace")
    public String marketplace(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserConfiguration.partnerMenuElements,
                "partnerPositions", List.of(
                        new Job(1, new Partner("Warlock Digital"), new Date(), new Date(), Period.of(0,6,0), "Geraldton", "Build Android version of Ill Technique App", false, false, 36.50, PayType.WAGE, Frequency.WEEKLY),
                        new Job(2, new Partner("Google"), new Date(), new Date(), Period.of(0,0,1), "Perth", "A 2nd sample job", false, true, 27.50, PayType.WAGE, Frequency.WEEKLY),
                        new Placement(3, new Partner("Apple"), new Date(), new Date(), Period.of(1,0,0), "Sydney", "A placement example", false, true, false)
                )
        ));
        return "/partner/marketplace";
    }

    @GetMapping("/new-position")
    public String newPosition (Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserConfiguration.partnerMenuElements,
                "positionOptions", List.of("Select...", "Job", "Placement" )
        ));
        return "/partner/new-position";
    }

    @GetMapping("/edit-position")
    public String newPosition (Model model, @RequestParam String type) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserConfiguration.partnerMenuElements,
                "positionType", type
        ));
        return "/partner/edit-position";
    }

    /*
    Todo:
        - ADP-78: Forum
        - ADP-79: Job & Placement Management
        - ADP-80: Profile
        - ADP-82: Expressions Of Interest
     */
}
