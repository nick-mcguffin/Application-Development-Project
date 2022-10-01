package com.wilma.web.controller.web.portal;

import com.wilma.config.web.UserPortalConfiguration;
import com.wilma.entity.dto.JobDTO;
import com.wilma.entity.dto.PlacementDTO;
import com.wilma.entity.dto.PostDTO;
import com.wilma.entity.dto.ReplyDTO;
import com.wilma.entity.positions.Placement;
import com.wilma.entity.users.Partner;
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
@RequestMapping ("/partner")
public class PartnerPortalController {

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
                "menuElements", UserPortalConfiguration.partnerMenuElements
        ));
        return "/partner/dashboard";
    }
    //endregion

    //region Marketplace
    @GetMapping("/marketplace")
    public String marketplace(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserPortalConfiguration.partnerMenuElements,
                "partnerJobs", positionService.getJobs(),
                "partnerPlacements", positionService.getPlacements(),
                "partnerEOIPositions", eoiService.getExpressionsOfInterest()
        ));
        return "/partner/marketplace";
    }

    @GetMapping("/new-position")
    public String newPosition (Model model, @RequestParam String type) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserPortalConfiguration.partnerMenuElements,
                "type", type,
                "job", new JobDTO(),
                "placement", new PlacementDTO()
        ));
        return "/partner/new-position";
    }

    @GetMapping("/edit-position")
    public String editPosition (Model model, @RequestParam String type, @RequestParam Integer id) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserPortalConfiguration.partnerMenuElements,
                "type", type,
                "id", id,
                "placement", positionService.findById(id),
                "job", positionService.findById(id)
        ));
        return "/partner/edit-position";
    }

    @GetMapping("/add-review")
    public String addReview(Model model, @RequestParam String type, @RequestParam Integer id) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserPortalConfiguration.partnerMenuElements,
                "type", type,
                "id", id,
                "placement", positionService.findById(id)
        ));
        return "/partner/add-review";
    }
    @PostMapping("/create-job")
    public RedirectView createJob(@ModelAttribute JobDTO jobDTO, Model model){
        var newJob = positionService.addJobFromDTO(jobDTO);
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserPortalConfiguration.partnerMenuElements,
                "job", jobDTO));

        log.info("Job created from DTO: "+ newJob);
        return new RedirectView("/partner/marketplace");
    }

    @PostMapping("/create-placement")
    public RedirectView createPlacement(@ModelAttribute PlacementDTO placementDTO, Model model){
        var newPlacement = positionService.addPlacementFromDTO(placementDTO);
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserPortalConfiguration.partnerMenuElements,
                "placement", placementDTO));

        log.info("Placement created from DTO: "+ newPlacement);
        return new RedirectView("/partner/marketplace");
    }

    @PostMapping("/update-placement")
    public RedirectView updatePlacement(@ModelAttribute PlacementDTO placementDTO, @RequestParam Integer id, Model model){
        var newPlacement = positionService.updatePlacementFromDTO(placementDTO);
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserPortalConfiguration.partnerMenuElements,
                "placement", placementDTO,
                "id", id
        ));

        log.info("Placement updated from DTO: "+ newPlacement);
        return new RedirectView("/partner/marketplace");
    }

    @PostMapping("/update-job")
    public RedirectView updateJob(@ModelAttribute JobDTO jobDTO, @RequestParam Integer id, Model model){
        var newJob = positionService.updateJobFromDTO(jobDTO);
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserPortalConfiguration.partnerMenuElements,
                "job", jobDTO,
                "id", id
        ));

        log.info("Job: {} updated from DTO: {}", newJob, jobDTO);
        return new RedirectView("/partner/marketplace");
    }

    @PostMapping("/update-review")
    public String updateReview(@ModelAttribute Placement placement) throws IOException {
       positionService.AddReview(placement);

                return "redirect:/partner/marketplace";
    }
    //endregion

    //region Forum
    @GetMapping("/forum")
    public String forumOverview(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserPortalConfiguration.partnerMenuElements,
                "categoryList", categoryService.findAll(),
                "recentPosts", forumService.getPosts()
        ));
        return "/partner/forum/overview";
    }

    @GetMapping("/forum-content")
    public String forumContent(@RequestParam String type, Model model, @RequestParam(required = false) Integer postId) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserPortalConfiguration.partnerMenuElements,
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
                "menuElements", UserPortalConfiguration.partnerMenuElements,
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
        var categoryName = newPost.getCategory().getName();
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserPortalConfiguration.partnerMenuElements,
                "availableCategories", categoryService.findAll(),
                "availableTags", tagService.findAll(),
                "categoryName", categoryName,
                "postsByCategory", forumService.getPostByCategoryName(categoryName),
                "repliesForPosts", forumService.getPostRepliesByCategory(categoryName),
                "post", postDTO));

        log.info("Post created from DTO: "+ newPost);
        return new RedirectView("/partner/forum");
    }

    @GetMapping("/forum-thread")
    public String forumThread(@RequestParam String category, Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserPortalConfiguration.partnerMenuElements,
                "categoryName", category,
                "postsByCategory", forumService.getPostByCategoryName(category),
                "repliesForPosts", forumService.getPostRepliesByCategory(category)));
        return "/partner/forum/forum-thread";

    }
    //endregion

    //region Expressions of interest
    @GetMapping("/expressions-of-interest")
    public String expressionsOfInterest(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "expressions-of-interest",
                "menuElements", UserPortalConfiguration.partnerMenuElements,

                "openExpressionsOfInterest", eoiService.getExpressionsOfInterest()
        ));
        return "/partner/expressions-of-interest";
    }

    @GetMapping("/new-placement-from-eoi")
    public String newPlacementFromEOI (Model model, @RequestParam Integer id) {
        var eoi = eoiService.findEOIById(id);
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserPortalConfiguration.partnerMenuElements,
                "placement", new Placement(null, null, eoi.getDate(), eoi.getDate(), null, eoi.getLocation(), "(EOI: " + eoi.getId() + ", Category: " + eoi.getCategory() + ") " + eoi.getDescription(), false, false,false),
                "id", id
        ));
        return "/partner/new-placement-from-eoi";
    }
    //endregion

    //region Profile
    @GetMapping("/profile")
    public String partnerProfile(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "profile",
                "menuElements", UserPortalConfiguration.partnerMenuElements,
                "currentUser", userService.getCurrentUser(),
                "inEditMode", false
        ));
        return "/partner/profile";
    }

    @GetMapping("/edit-profile")
    public String editProfile(Model model, HttpServletRequest request){
        model.addAllAttributes(Map.of(
                "currentPage", "profile",
                "menuElements", UserPortalConfiguration.partnerMenuElements,
                "inEditMode", true,
                "currentUser", userService.getCurrentUser()
        ));
        return "/partner/profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute Partner partner, @RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        userService.updatePartnerProfile(partner, file.isEmpty() ?
                ((Partner) userService.getCurrentUser()).getProfileImageId() :
                documentService.uploadFile(file).getId()
        );
        return "redirect:profile";
    }
    //endregion

}
