package com.wilma.web.controller.web.portal;

import com.wilma.config.web.UserConfiguration;
import com.wilma.entity.Frequency;
import com.wilma.entity.PayType;
import com.wilma.entity.dto.PostDTO;
import com.wilma.entity.dto.ReplyDTO;
import com.wilma.entity.positions.Job;
import com.wilma.entity.positions.Placement;
import com.wilma.entity.users.Partner;
import com.wilma.entity.users.Resume;
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
@RequestMapping("/student")
public class StudentPortalController {

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
                "menuElements", UserConfiguration.studentMenuElements
        ));
        return "/student/dashboard";
    }
    //endregion

    //region Jobs & placements (marketplace)
    @GetMapping("/marketplace")
    public String marketplace(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserConfiguration.studentMenuElements,
                "approvedPositions", List.of(
                        new Job(1, new Partner("Microsoft", "Microsoft"), new Date(), new Date(), Period.of(0,0,1), "Brisbane", "A sample job", false, false, 25.50, PayType.WAGE, Frequency.WEEKLY),
                        new Job(2, new Partner("Google", "Google"), new Date(), new Date(), Period.of(0,11,1), "Perth", "A 2nd sample job", false, true, 27.50, PayType.WAGE, Frequency.WEEKLY),
                        new Placement(3, new Partner("Apple", "Apple"), new Date(), new Date(), Period.of(1,0,0), "Sydney", "A placement example", false, false, false)
                )

        ));
        return "/student/marketplace";
    }
    //endregion

    //region Forum
    @GetMapping("/forum")
    public String forumOverview(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserConfiguration.studentMenuElements,
                "categoryList", categoryService.findAll(),
                "recentPosts", forumService.getPosts()
        ));
        return "/student/forum/overview";
    }

    @GetMapping("/forum-content")
    public String forumContent(@RequestParam String type, Model model, @RequestParam(required = false) Integer postId) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserConfiguration.studentMenuElements,
                "availableCategories", categoryService.findAll(),
                "availableTags", tagService.findAll(),
                "contentType", type,
                "post", new PostDTO(),
                "reply", new ReplyDTO()));
        if(postId != null)
            model.addAttribute("postId", postId);
        return "/student/forum/forum-content";
    }

    @PostMapping("/reply-to-post")
    public RedirectView replyToPost(@ModelAttribute ReplyDTO replyDTO, @RequestParam Integer postId, Model model){
        var reply = forumService.addReplyFromDTO(replyDTO);
        var category = reply.getPost().getCategory().getName();
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserConfiguration.studentMenuElements,
                "postId", postId,
                "categoryName", category,
                "postsByCategory", forumService.getPostByCategoryName(category),
                "repliesForPosts", forumService.getPostRepliesByCategory(category),
                "reply", replyDTO));

        log.info("Reply added: "+ reply);
        return new RedirectView("/student/forum-thread?category="+ category);
    }
    @PostMapping("/create-post")
    public RedirectView createPost(@ModelAttribute PostDTO postDTO, Model model){
        var newPost = forumService.addPostFromDTO(postDTO);
        var categoryName = newPost.getCategory().getName();
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserConfiguration.studentMenuElements,
                "availableCategories", categoryService.findAll(),
                "availableTags", tagService.findAll(),
                "categoryName", categoryName,
                "postsByCategory", forumService.getPostByCategoryName(categoryName),
                "repliesForPosts", forumService.getPostRepliesByCategory(categoryName),
                "post", postDTO));

        log.info("Post created from DTO: "+ newPost);
        return new RedirectView("/student/forum");
    }

    @GetMapping("/forum-thread")
    public String forumThread(@RequestParam String category, Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserConfiguration.studentMenuElements,
                "categoryName", category,
                "postsByCategory", forumService.getPostByCategoryName(category),
                "repliesForPosts", forumService.getPostRepliesByCategory(category)));
        return "/student/forum/forum-thread";
    }
    //endregion

    //region Resume management
    @GetMapping("/resume-management")
    public String resumeManagement(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "Resume Management",
                "menuElements", UserConfiguration.studentMenuElements,
                "studentResumes", List.of(
                new Resume("Resume 1", "pdf", new java.util.Date()),new Resume("Resume 2", "pdf", new java.util.Date()),
                        new Resume("Resume 3", "pdf", new java.util.Date()),
                        new Resume("Resume 4", "pdf", new java.util.Date()))

        ));
        return "/student/resume-management";
    }
    //endregion

    //region Profile
    @GetMapping("/profile")
    public String studentProfile(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "profile",
                "menuElements", UserConfiguration.studentMenuElements
        ));
        return "/student/profile";
    }
    //endregion

}
