package com.wilma.web.controller.web.portal;

import com.wilma.config.web.UserPortalConfiguration;
import com.wilma.entity.dto.*;
import com.wilma.entity.users.Educator;
import com.wilma.service.UserService;
import com.wilma.service.docs.DocumentService;
import com.wilma.service.forum.CategoryService;
import com.wilma.service.forum.ForumService;
import com.wilma.service.forum.TagService;
import com.wilma.service.positions.EOIService;
import com.wilma.service.positions.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    @Autowired
    PositionService positionService;
    @Autowired
    UserService userService;
    @Autowired
    DocumentService documentService;

    @Autowired
    EOIService eoiService;

    //region Dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "dashboard",
                "menuElements", UserPortalConfiguration.educatorMenuElements
        ));
        return "/educator/dashboard";
    }
    //endregion

    //region Jobs & placements (marketplace)
    @GetMapping("/marketplace")
    public String marketplace(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserPortalConfiguration.educatorMenuElements,
                "approvedPositions", positionService.findAll(),
                "pendingPositions", positionService.pendingPositions()
        ));
        return "/educator/marketplace";
    }

    @GetMapping("/marketplace-approve")
    public String marketplaceSetApproved(Model model, @RequestParam Integer posId) {
        positionService.setApproved(posId);
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserPortalConfiguration.educatorMenuElements,
                "approvedPositions", positionService.findAll(),
                "pendingPositions", positionService.pendingPositions()
        ));
        return "redirect:marketplace";
    }
    //endregion

    //region EOIs
    @GetMapping("/new-expression-of-interest")
    public String newExpressionOfInterest(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserPortalConfiguration.educatorMenuElements,
                "eoi", new ExpressionOfInterestDTO()
        ));
        return "/educator/new-expression-of-interest";
    }

    @GetMapping("/edit-expression-of-interest")
    public String editExpressionOfInterest(@RequestParam Integer id, Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserPortalConfiguration.educatorMenuElements,
                "eoi", eoiService.findEOIById(id),
                "id", id
        ));
        return "/educator/edit-expression-of-interest";
    }

    @PostMapping("/update-expression-of-interest")
    public RedirectView updatePlacement(@ModelAttribute ExpressionOfInterestDTO expressionOfInterestDTO, @RequestParam Integer id, Model model){
        var newEOI = eoiService.updateEOIFromDTO(expressionOfInterestDTO);
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserPortalConfiguration.educatorMenuElements,
                "eoi", expressionOfInterestDTO,
                "id", id
        ));

        log.info("EOI updated from DTO: "+ newEOI);
        return new RedirectView("/educator/expressions-of-interest");
    }

    @PostMapping("/create-expression-of-interest")
    public RedirectView createEOI(@ModelAttribute ExpressionOfInterestDTO eoiDTO, Model model){
        var newEOI = eoiService.addEOIFromDTO(eoiDTO);
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserPortalConfiguration.educatorMenuElements,
                "eoi", eoiDTO));

        log.info("EOI created from DTO: "+ newEOI);
        return new RedirectView("/educator/expressions-of-interest");
    }
    //endregion

    //region Forum
    @GetMapping("/forum")
    public String forumOverview(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserPortalConfiguration.educatorMenuElements,
                "categoryList", categoryService.findAll(),
                "recentPosts", forumService.getPosts()
        ));
        return "/educator/forum/overview";
    }

    @GetMapping("/forum-content")
    public String forumContent(@RequestParam String type, Model model, @RequestParam(required = false) Integer postId) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserPortalConfiguration.educatorMenuElements,
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
                "menuElements", UserPortalConfiguration.educatorMenuElements,
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
                "menuElements", UserPortalConfiguration.educatorMenuElements,
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
                "menuElements", UserPortalConfiguration.educatorMenuElements,
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
                "menuElements", UserPortalConfiguration.educatorMenuElements,
                "categoryName", category,
                "postsByCategory", forumService.getPostByCategoryName(category),
                "repliesForPosts", forumService.getPostRepliesByCategory(category)));
        return new RedirectView("/educator/forum-thread?category=" + category);
    }
    //endregion

    //region Expressions of interest
    @GetMapping("/expressions-of-interest")
    public String expressionsOfInterest(Model model) {
        model.addAllAttributes(Map.of(
            "currentPage", "expressions-of-interest",
                "menuElements", UserPortalConfiguration.educatorMenuElements,
                "openExpressionsOfInterest", eoiService.getExpressionsOfInterest()
        ));
        return "/educator/expressions-of-interest";
    }
    //endregion

    //region Profile
    @GetMapping("/profile")
    public String EducatorProfile(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "profile",
                "menuElements", UserPortalConfiguration.educatorMenuElements,
                "currentUser", userService.getCurrentUser(),
                "inEditMode", false
        ));
        return "/educator/profile";
    }

    @GetMapping("/edit-profile")
    public String editProfile(Model model, HttpServletRequest request){
        model.addAllAttributes(Map.of(
                "currentPage", "profile",
                "menuElements", UserPortalConfiguration.educatorMenuElements,
                "inEditMode", true,
                "currentUser", userService.getCurrentUser()
        ));
        return "/educator/profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute Educator educator, @RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        userService.updateEducatorProfile(educator, file.isEmpty() ?
                ((Educator) userService.getCurrentUser()).getProfileImageId() :
                documentService.uploadFile(file).getId()
        );
        return "redirect:profile";
    }
    //endregion

    //region Position
    @GetMapping("/edit-position")
    public String editPosition (Model model, @RequestParam String type, @RequestParam Integer id) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserPortalConfiguration.educatorMenuElements,
                "type", type,
                "id", id,
                "placement", positionService.findById(id),
                "job", positionService.findById(id)
        ));
        return "/educator/edit-position";
    }

    @PostMapping("/update-placement")
    public RedirectView updatePlacement(@ModelAttribute PlacementDTO placementDTO, @RequestParam Integer id, Model model){
        var newPlacement = positionService.updatePlacementFromDTO(placementDTO);
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserPortalConfiguration.educatorMenuElements,
                "placement", placementDTO,
                "id", id
        ));

        log.info("Placement updated from DTO: "+ newPlacement);
        return new RedirectView("/educator/marketplace");
    }

    @PostMapping("/update-job")
    public RedirectView updateJob(@ModelAttribute JobDTO jobDTO, @RequestParam Integer id, Model model){
        var newJob = positionService.updateJobFromDTO(jobDTO);
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserPortalConfiguration.educatorMenuElements,
                "job", jobDTO,
                "id", id
        ));

        log.info("Job: {} updated from DTO: {}", newJob, jobDTO);
        return new RedirectView("/educator/marketplace");
    }
    //end region

}
