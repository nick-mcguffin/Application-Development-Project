package com.wilma.config.web;

import com.wilma.entity.navigation.SidebarMenuElement;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Configuration
public class UserConfiguration {

    public final static List<SidebarMenuElement> educatorMenuElements = List.of(
            new SidebarMenuElement("dashboard", "dashboard", "house", "Dashboard"),
            new SidebarMenuElement("marketplace", "marketplace", "business_center", "Jobs & Placements"),
            new SidebarMenuElement("forum", "forum", "forum", "Q&A Forum"),
            new SidebarMenuElement("expressions-of-interest", "expressions-of-interest", "file_present", "Expressions Of Interest"),
            new SidebarMenuElement("profile", "profile", "manage_accounts", "Profile")
    );
    //Todo
    public final static List<SidebarMenuElement> partnerMenuElements = List.of(
            new SidebarMenuElement("dashboard", "dashboard", "house", "Dashboard"),
            new SidebarMenuElement("marketplace", "marketplace", "house", "Jobs & Placements"),
            new SidebarMenuElement("forum", "forum", "forum", "Q&A Forum"),
            new SidebarMenuElement("expressions-of-interest", "expressions-of-interest", "file_present", "Expressions Of Interest"),
            new SidebarMenuElement("profile", "profile", "manage_accounts", "Profile")
    );
    //Todo
    public final static List<SidebarMenuElement> studentMenuElements = List.of(
            new SidebarMenuElement("dashboard", "dashboard", "house", "Dashboard"),
            new SidebarMenuElement("marketplace", "marketplace", "house", "Jobs & Placements"),
            new SidebarMenuElement("forum", "forum", "forum", "Q&A Forum"),
            new SidebarMenuElement("resume_management", "resume_management", "contact_page", "Resume Management"),
            new SidebarMenuElement("profile", "profile", "manage_accounts", "Profile")
    );


}
