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
    @GetMapping("/marketplace")
    public String marketplace(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserConfiguration.studentMenuElements,
                "approvedPositions", List.of(
                        new Job(1, new Partner("Microsoft"), new Date(), new Date(), Period.of(0,0,1), "Brisbane", "A sample job", false, false, 25.50, PayType.WAGE, Frequency.WEEKLY),
                        new Job(2, new Partner("Google"), new Date(), new Date(), Period.of(0,11,1), "Perth", "A 2nd sample job", false, true, 27.50, PayType.WAGE, Frequency.WEEKLY),
                        new Placement(3, new Partner("Apple"), new Date(), new Date(), Period.of(1,0,0), "Sydney", "A placement example", false, false, false)
                )

        ));
        return "/student/marketplace";
    }
}
