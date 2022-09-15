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

    @GetMapping("/expressions-of-interest")
    public String expressionsOfInterest(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "Expressions Of Interest",
                "menuElements", UserConfiguration.partnerMenuElements,
                
                "pendingPartnerExpressionsOfInterest", List.of(
                        new ExpressionOfInterest(1, new Partner("Microsoft"), new Date(), new Date(), Period.of(0,0,1), "Brisbane", "A sample job", false, false),
                        new ExpressionOfInterest(2, new Partner("Google"), new Date(), new Date(), Period.of(0,11,1), "Perth", "Another sample job", false, false),
                        new ExpressionOfInterest(3, new Partner("Apple"), new Date(), new Date(), Period.of(1,0,0), "Sydney", "A placement example", false, false),
                        new ExpressionOfInterest(4, new Partner("Amazon"), new Date(), new Date(), Period.of(1,1,1), "Melbourne", "Slavery with extra steps", false, false)
                )
        ));
        return "/partner/expressions-of-interest";
    }
    /*
    Todo:
        - ADP-77: Jobs & Placements
        - ADP-78: Forum
        - ADP-79: Job & Placement Management
        - ADP-80: Profile
     */
}
