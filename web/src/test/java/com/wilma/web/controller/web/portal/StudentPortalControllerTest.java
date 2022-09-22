package com.wilma.web.controller.web.portal;

import com.wilma.config.web.UserDocumentConfiguration;
import com.wilma.entity.docs.UserDocument;
import com.wilma.service.docs.DocumentService;
import com.wilma.service.forum.CategoryService;
import com.wilma.service.forum.ForumService;
import com.wilma.service.forum.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentPortalControllerTest {
    @Mock
    private ConcurrentModel model;
    @Mock
    private CategoryService categoryService;
    @Mock
    private ForumService forumService;
    @Mock
    private TagService tagService;
    @Mock
    private DocumentService documentService;
    @Mock
    private UserDocumentConfiguration userDocumentConfiguration;
    @Mock
    private MultipartFile file;
    @Autowired
    private MockHttpServletRequest request;
    @InjectMocks
    private StudentPortalController controller;

    private UserDocument userDocument;


    @BeforeEach
    void setUp() {
        request.setRemoteAddr("192.168.1.10");
        when(userDocumentConfiguration.getUploadSizeLimit()).thenReturn("10");
        when(documentService.findAllForUser()).thenReturn(List.of(new UserDocument()));
        userDocument = new UserDocument(null, new Date(), "willy-wonka.png", "src\\test\\resources\\willy-wonka.png", null);
    }

    @Test
    void dashboard() {
        assertEquals("/student/dashboard", controller.dashboard(model));
    }

    @Test
    void marketplace() {
        assertEquals("/student/marketplace", controller.marketplace(model));
    }

    @Test
    void forum() {
        assertEquals("/student/forum/overview", controller.forumOverview(model));
    }

    @Test
    void forumContent() {
        assertEquals("/student/forum/forum-content", controller.forumContent("post", model, null));
    }

    @Test
    void replyToPost() {
    }

    @Test
    void createPost() {
    }

    @Test
    void forumThread() {
        assertEquals("/student/forum/forum-thread", controller.forumThread("post", model));
    }

    @Test
    void resumeManagement() {
        assertEquals("/student/resume-management", controller.resumeManagement(model));
    }

   @Test
   void uploadDocument() throws IOException {
       assertEquals("redirect:/student/resume-management", controller.uploadDocument(file, null, request));
   }

    @Test
    void openDocumentInNewTab() throws IOException {
        when(documentService.findById(any())).thenReturn(userDocument);
        assertTrue(controller.openDocumentInNewTab(1, request).getStatusCode().is2xxSuccessful());
    }

    @Test
    void downloadDocument() throws IOException {
        when(documentService.findById(any())).thenReturn(userDocument);
        assertTrue(controller.downloadDocument(1).getStatusCode().is2xxSuccessful());
    }

    @Test
    void deleteDocument() {
        when(documentService.findById(any())).thenReturn(userDocument);
        assertEquals("redirect:/student/resume-management", controller.deleteDocument(null, request));

    }
}