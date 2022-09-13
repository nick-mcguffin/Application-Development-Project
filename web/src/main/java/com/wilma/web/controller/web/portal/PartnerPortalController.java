package com.wilma.web.controller.web.portal;

import com.wilma.config.web.UserConfiguration;
import com.wilma.entity.Frequency;
import com.wilma.entity.PayType;
import com.wilma.entity.dto.PostDTO;
import com.wilma.entity.dto.ReplyDTO;
import com.wilma.entity.positions.Job;
import com.wilma.entity.positions.Placement;
import com.wilma.entity.users.Partner;
import com.wilma.service.forum.CategoryService;
import com.wilma.service.forum.ForumService;
import com.wilma.service.forum.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping ("/partner")
public class PartnerPortalController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ForumService forumService;
    @Autowired
    TagService tagService;

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
                        new Job(1, new Partner("Warlock Digital", "Warlock Digital"), new Date(), new Date(), Period.of(0,6,0), "Geraldton", "Build Android version of Ill Technique App", false, false, 36.50, PayType.WAGE, Frequency.WEEKLY),
                        new Job(2, new Partner("Google", "Google"), new Date(), new Date(), Period.of(0,0,1), "Perth", "A 2nd sample job", false, true, 27.50, PayType.WAGE, Frequency.WEEKLY),
                        new Placement(3, new Partner("Apple", "Apple"), new Date(), new Date(), Period.of(1,0,0), "Sydney", "A placement example", false, true, false)
                )
        ));
        return "/partner/marketplace";
    }

    @GetMapping("/forum")
    public String forumOverview(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserConfiguration.educatorMenuElements,
                "categoryList", categoryService.findAll(),
                "recentPosts", forumService.getPosts()
        ));
        return "/partner/forum/overview";
    }

    @GetMapping("/forum-content")
    public String forumContent(@RequestParam String type, Model model, @RequestParam(required = false) Integer postId) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserConfiguration.educatorMenuElements,
                "availableCategories", categoryService.findAll(),
                "availableTags", tagService.findAll(),
                "contentType", type,
                "post", new PostDTO(),
                "reply", new ReplyDTO()));
        if(postId != null)
            model.addAttribute("postId", postId);
        return "/partner/forum/forum-content";
    }

    @PostMapping("/reply-to-post")
    public RedirectView replyToPost(@ModelAttribute ReplyDTO replyDTO, @RequestParam Integer postId, Model model){
        var reply = forumService.addReplyFromDTO(replyDTO);
        var category = reply.getPost().getCategory().getName();
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserConfiguration.educatorMenuElements,
                "postId", postId,
                "categoryName", category,
                "postsByCategory", forumService.getPostByCategoryName(category),
                "repliesForPosts", forumService.getPostRepliesByCategory(category),
                "reply", replyDTO));

        log.info("Reply added: "+ reply);
        return new RedirectView("/partner/forum-thread?category="+ category);
    }
    @PostMapping("/create-post")
    public RedirectView createPost(@ModelAttribute PostDTO postDTO, Model model){
        var newPost = forumService.addPostFromDTO(postDTO);
        var category = newPost.getCategory().getName();
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserConfiguration.educatorMenuElements,
                "availableCategories", categoryService.findAll(),
                "availableTags", tagService.findAll(),
                "categoryName", category,
                "postsByCategory", forumService.getPostByCategoryName(category),
                "repliesForPosts", forumService.getPostRepliesByCategory(category),
                "post", postDTO));

        log.info("Post created from DTO: "+ newPost);
        return new RedirectView("/partner/forum");
    }


    @GetMapping("/forum-thread")
    public String forumThread(@RequestParam String category, Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserConfiguration.educatorMenuElements,
                "categoryName", category,
                "postsByCategory", forumService.getPostByCategoryName(category),
                "repliesForPosts", forumService.getPostRepliesByCategory(category)));
        return "/partner/forum/forum-thread";
    }

    /*
    Todo:
        - ADP-77: Jobs & Placements
        - ADP-79: Job & Placement Management
        - ADP-80: Profile
        - ADP-82: Expressions Of Interest
     */
}
