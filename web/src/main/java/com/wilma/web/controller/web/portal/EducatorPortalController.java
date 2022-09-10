package com.wilma.web.controller.web.portal;

import com.wilma.config.web.UserConfiguration;
import com.wilma.entity.Frequency;
import com.wilma.entity.PayType;
import com.wilma.entity.positions.ExpressionOfInterest;
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

    @GetMapping("/marketplace")
    public String marketplace(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserConfiguration.educatorMenuElements,
                "approvedPositions", List.of(
                        new Job(1, new Partner("Microsoft"), new Date(), new Date(), Period.of(0,0,1), "Brisbane", "A sample job", false, false, 25.50, PayType.WAGE, Frequency.WEEKLY),
                        new Job(2, new Partner("Google"), new Date(), new Date(), Period.of(0,11,1), "Perth", "A 2nd sample job", false, true, 27.50, PayType.WAGE, Frequency.WEEKLY),
                        new Placement(3, new Partner("Apple"), new Date(), new Date(), Period.of(1,0,0), "Sydney", "A placement example", false, false, false)
                ),
                "pendingPositions", List.of(
                        new Job(1, new Partner("Microsoft"), new Date(), new Date(), Period.of(0,0,1), "Brisbane", "A sample job", false, true, 25.50, PayType.WAGE, Frequency.WEEKLY),
                        new Job(2, new Partner("Google"), new Date(), new Date(), Period.of(0,11,1), "Perth", "A 2nd sample job", false, false, 27.50, PayType.WAGE, Frequency.WEEKLY),
                        new Placement(3, new Partner("Apple"), new Date(), new Date(), Period.of(1,0,0), "Sydney", "A placement example", false, false, false)
                )
        ));
        return "/educator/marketplace";
    }
    @GetMapping("/expressions-of-interest")
    public String expressionsOfInterest(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "Expressions Of Interest",
                "menuElements", UserConfiguration.educatorMenuElements,
                "approvedPositions", List.of(
                        new ExpressionOfInterest(1, new Partner("Microsoft"), new Date(), new Date(), Period.of(0,0,1), "Brisbane", "A sample job", false, false, "Network", "Network and Communications Engineer", "Placement", true),
                        new ExpressionOfInterest(2, new Partner("Google"), new Date(), new Date(), Period.of(0,11,1), "Perth", "Another sample job", false, false, "Software Development", "Software Engineer", "Job", true),
                        new ExpressionOfInterest(3, new Partner("Apple"), new Date(), new Date(), Period.of(1,0,0), "Sydney", "A placement example", false, false, "Business Analysis", "Business Analysit", "Placement", true),
                        new ExpressionOfInterest(4, new Partner("Amazon"), new Date(), new Date(), Period.of(1,1,1), "Melbourne", "Slavery with extra steps", false, false, "Project Management", "Technical Lead Engineer", "Job", true)
                ),
                "pendingPositions", List.of(
                        new ExpressionOfInterest(1, new Partner("Microsoft"), new Date(), new Date(), Period.of(0,0,1), "Brisbane", "A sample job", false, false, "Network", "Network and Communications Engineer", "Placement", true),
                        new ExpressionOfInterest(2, new Partner("Google"), new Date(), new Date(), Period.of(0,11,1), "Perth", "Another sample job", false, false, "Software Development", "Software Engineer", "Job", true),
                        new ExpressionOfInterest(3, new Partner("Apple"), new Date(), new Date(), Period.of(1,0,0), "Sydney", "A placement example", false, false, "Business Analysis", "Business Analysit", "Placement", true),
                        new ExpressionOfInterest(4, new Partner("Amazon"), new Date(), new Date(), Period.of(1,1,1), "Melbourne", "Slavery with extra steps", false, false, "Project Management", "Technical Lead Engineer", "Job", true)
                )
        ));
        return "/educator/expressions-of-interest";
    }

    @GetMapping("/forum")
    public String forumOverview(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserConfiguration.educatorMenuElements
        ));
        return "/educator/forum/overview";
    }

    @GetMapping("/forum-content")
    public String forumContent(@RequestParam String type, Model model) {
            model.addAllAttributes(Map.of(
                            "currentPage", "forum",
                            "menuElements", UserConfiguration.educatorMenuElements,
                            "contentType", type));
            return "/educator/forum/forum-content";
    }

    @GetMapping("/forum-thread")
    public String forumThread(@RequestParam String category, Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserConfiguration.educatorMenuElements,
                "category", category));
        return "/educator/forum/forum-thread";
    }

    /*
    Todo:
        - ADP-74: Jobs & Placement Management
        - ADP-81: Expressions Of Interest
        - ADP-73: Profile
     */
}
