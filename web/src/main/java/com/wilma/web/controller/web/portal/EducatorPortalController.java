package com.wilma.web.controller.web.portal;

import com.wilma.config.web.UserConfiguration;
import com.wilma.entity.Frequency;
import com.wilma.entity.PayType;
import com.wilma.entity.positions.ExpressionOfInterest;
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
@RequestMapping("/educator")
public class EducatorPortalController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ForumService forumService;
    @Autowired
    TagService tagService;

    //region Dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "dashboard",
                "menuElements", UserConfiguration.educatorMenuElements
        ));
        return "/educator/dashboard";
    }
    //endregion

    //region Profile
    @GetMapping("/profile")
    public String EducatorProfile(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "Profile",
                "menuElements", UserConfiguration.educatorMenuElements
        ));
        return "/educator/profile";
    }
    //endregion

    //region Jobs & placements (marketplace)
    @GetMapping("/marketplace")
    public String marketplace(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserConfiguration.educatorMenuElements,
                "approvedPositions", List.of(
                        new Job(1, new Partner("microsoft", "Microsoft"), new Date(), new Date(), Period.of(0,0,1), "Brisbane", "A sample job", false, true, 25.50, PayType.WAGE, Frequency.WEEKLY),
                        new Job(2, new Partner("google", "Google"), new Date(), new Date(), Period.of(0,11,1), "Perth", "A 2nd sample job", false, true, 27.50, PayType.WAGE, Frequency.WEEKLY),
                        new Placement(3, new Partner("apple", "Apple"), new Date(), new Date(), Period.of(1,0,0), "Sydney", "A placement example", false, true, false)
                ),
                "pendingPositions", List.of(
                        new Job(1, new Partner("microsoft", "Microsoft"), new Date(), new Date(), Period.of(0,0,1), "Brisbane", "A sample job", false, true, 25.50, PayType.WAGE, Frequency.WEEKLY),
                        new Job(2, new Partner("google", "Google"), new Date(), new Date(), Period.of(0,11,1), "Perth", "A 2nd sample job", false, true, 27.50, PayType.WAGE, Frequency.WEEKLY),
                        new Placement(3, new Partner("apple", "Apple"), new Date(), new Date(), Period.of(1,0,0), "Sydney", "A placement example", false, true, false)
                )
        ));
        return "/educator/marketplace";
    }
    //endregion

    @GetMapping("/expressions-of-interest")
    public String expressionsOfInterest(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "Expressions Of Interest",
                "menuElements", UserConfiguration.educatorMenuElements,
                "approvedPositions", List.of(
                        new ExpressionOfInterest(1, new Partner("Microsoft", "Microsoft"), new Date(), new Date(), Period.of(0,0,1), "Brisbane", "A sample job", false, false),
                        new ExpressionOfInterest(2, new Partner("Google", "Google"), new Date(), new Date(), Period.of(0,11,1), "Perth", "Another sample job", false, false),
                        new ExpressionOfInterest(3, new Partner("Apple", "Apple"), new Date(), new Date(), Period.of(1,0,0), "Sydney", "A placement example", false, false),
                        new ExpressionOfInterest(4, new Partner("Amazon", "Amazon"), new Date(), new Date(), Period.of(1,1,1), "Melbourne", "Slavery with extra steps", false, false)
                ),
                "pendingPositions", List.of(
                        new ExpressionOfInterest(1, new Partner("Microsoft", "Microsoft"), new Date(), new Date(), Period.of(0,0,1), "Brisbane", "A sample job", false, false),
                        new ExpressionOfInterest(2, new Partner("Google", "Google"), new Date(), new Date(), Period.of(0,11,1), "Perth", "Another sample job", false, false),
                        new ExpressionOfInterest(3, new Partner("Apple", "Apple"), new Date(), new Date(), Period.of(1,0,0), "Sydney", "A placement example", false, false),
                        new ExpressionOfInterest(4, new Partner("Amazon", "Amazon"), new Date(), new Date(), Period.of(1,1,1), "Melbourne", "Slavery with extra steps", false, false)
                )
        ));
        return "/educator/expressions-of-interest";
    }

    //region Forum
    @GetMapping("/forum")
    public String forumOverview(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserConfiguration.educatorMenuElements,
                "categoryList", categoryService.findAll(),
                "recentPosts", forumService.getPosts()
        ));
        return "/educator/forum/overview";
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
            return "/educator/forum/forum-content";
    }

    @PostMapping("/reply-to-post")
        public RedirectView replyToPost(@ModelAttribute ReplyDTO replyDTO, @RequestParam Integer postId, Model model){
        var reply = forumService.addReplyFromDTO(replyDTO);
        var categoryName = reply.getPost().getCategory().getName();
            model.addAllAttributes(Map.of(
                    "currentPage", "forum",
                    "menuElements", UserConfiguration.educatorMenuElements,
                    "postId", postId,
                    "categoryName", categoryName,
                    "postsByCategory", forumService.getPostByCategoryName(categoryName),
                    "repliesForPosts", forumService.getPostRepliesByCategory(categoryName),
                    "reply", replyDTO));

            log.info("Reply added: "+ reply);
            return new RedirectView("/educator/forum-thread?category="+ categoryName);
        }
    @PostMapping("/create-post")
    public RedirectView createPost(@ModelAttribute PostDTO postDTO, Model model){
        var newPost = forumService.addPostFromDTO(postDTO);
        var categoryName = newPost.getCategory().getName();
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserConfiguration.educatorMenuElements,
                "availableCategories", categoryService.findAll(),
                "availableTags", tagService.findAll(),
                "categoryName", categoryName,
                "postsByCategory", forumService.getPostByCategoryName(categoryName),
                "repliesForPosts", forumService.getPostRepliesByCategory(categoryName),
                "post", postDTO));

        log.info("Post created from DTO: "+ newPost);
        return new RedirectView("/educator/forum");
    }


    @GetMapping("/forum-thread")
    public String forumThread(@RequestParam String category, Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserConfiguration.educatorMenuElements,
                "categoryName", category,
                "postsByCategory", forumService.getPostByCategoryName(category),
                "repliesForPosts", forumService.getPostRepliesByCategory(category)));
        return "/educator/forum/forum-thread";
    }

    @GetMapping("/delete-post/{postId}")
    public RedirectView deleteForumPost(@PathVariable Integer postId, @RequestParam String category, Model model){
        forumService.deleteById(postId);
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserConfiguration.educatorMenuElements,
                "categoryName", category,
                "postsByCategory", forumService.getPostByCategoryName(category),
                "repliesForPosts", forumService.getPostRepliesByCategory(category)));
        return new RedirectView("/educator/forum-thread?category=" + category);
    }
    //endregion



    //region Todo: Expressions of interest
    //Add expressions of interest endpoint
    //endregion

}
