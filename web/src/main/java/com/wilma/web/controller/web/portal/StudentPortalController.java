package com.wilma.web.controller.web.portal;

import com.wilma.config.web.UserDocumentConfiguration;
import com.wilma.config.web.UserPortalConfiguration;
import com.wilma.entity.dto.ApplicationDTO;
import com.wilma.entity.dto.PostDTO;
import com.wilma.entity.dto.ReplyDTO;
import com.wilma.entity.positions.Job;
import com.wilma.entity.positions.Placement;
import com.wilma.entity.users.Student;
import com.wilma.service.UserService;
import com.wilma.service.docs.DocumentService;
import com.wilma.service.forum.CategoryService;
import com.wilma.service.forum.ForumService;
import com.wilma.service.forum.TagService;
import com.wilma.service.positions.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    @Autowired
    DocumentService documentService;
    @Autowired
    UserDocumentConfiguration userDocumentConfiguration;
    @Autowired
    PositionService positionService;

    @Autowired
    UserService userService;

    //region Dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "dashboard",
                "menuElements", UserPortalConfiguration.studentMenuElements
        ));
        return "/student/dashboard";
    }
    //endregion

    //region Jobs & placements (marketplace)
    @GetMapping("/marketplace")
    public String marketplace(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserPortalConfiguration.studentMenuElements,
                "job", new Job(),
                "placement", new Placement(),
                "allPositions", positionService.findAll()
        ));
        return "/student/marketplace";
    }

    @GetMapping("/marketplace-details")
    public String marketplaceDetails(Model model, @RequestParam String type, @RequestParam int id) {
        model.addAllAttributes(Map.of(
                "currentPage", "marketplace",
                "menuElements", UserPortalConfiguration.studentMenuElements,
                "type", type,
                "position", positionService.findById(id),
                "studentFiles", documentService.findAllForUser(),
                "application", new ApplicationDTO()
                ));
        return "/student/marketplace";
    }

    @PostMapping("/apply")
    public String apply(Model model, @ModelAttribute ApplicationDTO application){
        positionService.submitApplicationFromDTO(application);
        return "redirect:marketplace";
    }
    //endregion

    //region Forum
    @GetMapping("/forum")
    public String forumOverview(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserPortalConfiguration.studentMenuElements,
                "categoryList", categoryService.findAll(),
                "recentPosts", forumService.getPosts()
        ));
        return "/student/forum/overview";
    }

    @GetMapping("/forum-content")
    public String forumContent(@RequestParam String type, Model model, @RequestParam(required = false) Integer postId) {
        model.addAllAttributes(Map.of(
                "currentPage", "forum",
                "menuElements", UserPortalConfiguration.studentMenuElements,
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
                "menuElements", UserPortalConfiguration.studentMenuElements,
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
                "menuElements", UserPortalConfiguration.studentMenuElements,
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
                "menuElements", UserPortalConfiguration.studentMenuElements,
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
                "currentPage", "resume-management",
                "menuElements", UserPortalConfiguration.studentMenuElements,
                "sizeLimit", userDocumentConfiguration.getUploadSizeLimit(),
                "documents", documentService.findAllForUser()
        ));
        return "/student/resume-management";
    }

    @PostMapping("/resume-management")
    public String uploadDocument(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request) throws IOException {
        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("message","Please select a file to upload");
            return "redirect:/student/resume-management";
        }
        var savedFile = documentService.uploadFile(file);
        if(savedFile != null){
            log.info(savedFile.getFilename() +" saved by client with IP = "+ request.getRemoteAddr());
            redirectAttributes.addFlashAttribute("message","You successfully uploaded " + savedFile.getFilename());
        }
        return "redirect:/student/resume-management";
    }

    @GetMapping("/open")
    public ResponseEntity<?> openDocumentInNewTab(@RequestParam Integer documentId, HttpServletRequest request) throws IOException {
        var file = documentService.findById(documentId);
        log.info(file.getFilename() +" opened by client with IP = "+ request.getRemoteAddr());
        return ResponseEntity.ok()
                .header("Content-type", userDocumentConfiguration.getMediaType(file.getFilename()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"".concat(file.getFilename()).concat("\""))
                .body(Files.readAllBytes(Path.of(file.getFilepath())));
    }

    @GetMapping("/download")
    public ResponseEntity<?>downloadDocument(@RequestParam Integer documentId) throws IOException {
        var file = documentService.findById(documentId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"".concat(file.getFilename().concat("\"")))
                .body(Files.readAllBytes(Path.of(file.getFilepath())));
    }

    @GetMapping("/delete")
    public String deleteDocument(@RequestParam Integer documentId, HttpServletRequest request){
        var file = documentService.findById(documentId);
        log.info(file.getFilename() +" deleted by client with IP = "+ request.getRemoteAddr());
        documentService.deleteFile(file);
        return "redirect:/student/resume-management";
    }
    //endregion

    //region Profile
    @GetMapping("/profile")
    public String partnerProfile(Model model) {
        model.addAllAttributes(Map.of(
                "currentPage", "profile",
                "menuElements", UserPortalConfiguration.studentMenuElements,
                "currentUser", userService.getCurrentUser(),
                "inEditMode", false
        ));
        return "/student/profile";
    }

    @GetMapping("/edit-profile")
    public String editProfile(Model model, HttpServletRequest request){
        model.addAllAttributes(Map.of(
                "currentPage", "profile",
                "menuElements", UserPortalConfiguration.studentMenuElements,
                "inEditMode", true,
                "currentUser", userService.getCurrentUser()
        ));
        return "/student/profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute Student student, @RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        userService.updateStudentProfile(student, file.isEmpty() ?
                ((Student) userService.getCurrentUser()).getProfileImageId() :
                documentService.uploadFile(file).getId()
        );
        return "redirect:profile";
    }
    //endregion

}
