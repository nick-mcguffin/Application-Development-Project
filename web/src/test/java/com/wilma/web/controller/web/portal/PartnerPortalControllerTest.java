package com.wilma.web.controller.web.portal;

import com.wilma.service.forum.CategoryService;
import com.wilma.service.forum.ForumService;
import com.wilma.service.forum.TagService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ConcurrentModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PartnerPortalControllerTest {

    @Mock
    private ConcurrentModel model;
    @Mock
    private CategoryService categoryService;
    @Mock
    private ForumService forumService;
    @Mock
    private TagService tagService;
    @InjectMocks
    private PartnerPortalController controller;

    @Test
    void dashboard() {
        assertEquals("/partner/dashboard", controller.dashboard(model), "Unexpected dashboard endpoint");
    }

    @Test
    void marketplace() {
        assertEquals("/partner/marketplace", controller.marketplace(model), "Unexpected marketplace endpoint");
    }

    @Test
    void forum() {
        assertEquals("/partner/forum/overview", controller.forumOverview(model), "Unexpected forum endpoint");
    }

    @Test
    void forumContent() {
        assertEquals("/partner/forum/forum-content", controller.forumContent("post", model, 32), "Unexpected forum endpoint");
    }

    @Test
    void replyToPost() {
    }
    
    @Test
    void createPost() {
    }

    @Test
    void forumThread() {
        assertEquals("/partner/forum/forum-thread", controller.forumThread("post", model), "Unexpected forum endpoint");
    }

}