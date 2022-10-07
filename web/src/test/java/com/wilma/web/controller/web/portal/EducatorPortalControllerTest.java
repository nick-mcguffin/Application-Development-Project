package com.wilma.web.controller.web.portal;

import com.wilma.service.forum.CategoryService;
import com.wilma.service.forum.ForumService;
import com.wilma.service.forum.TagService;
import com.wilma.service.positions.EOIService;
import com.wilma.service.positions.PositionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ConcurrentModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EducatorPortalControllerTest {

    @Mock
    private ConcurrentModel model;
    @Mock
    private CategoryService categoryService;
    @Mock
    private ForumService forumService;
    @Mock
    private TagService tagService;
    @Mock
    private EOIService eoiService;
    @Mock
    private PositionService positionService;
    @InjectMocks
    private EducatorPortalController controller;

    @Test
    void dashboard() {
        assertEquals("/educator/dashboard", controller.dashboard(model), "Unexpected dashboard endpoint");
    }

    @Test
    void marketplace() {
        assertEquals("/educator/marketplace", controller.marketplace(model), "Unexpected marketplace endpoint");
    }

    @Test
    void forum() {
        assertEquals("/educator/forum/overview", controller.forumOverview(model), "Unexpected forum endpoint");
    }

    @Test
    void forumContent() {
        assertEquals("/educator/forum/forum-content", controller.forumContent("post", model, 32), "Unexpected forum endpoint");
    }

    @Test
    void replyToPost() {
    }
    
    @Test
    void createPost() {
    }

    @Test
    void forumThread() {
        assertEquals("/educator/forum/forum-thread", controller.forumThread("post", model), "Unexpected forum endpoint");
        assertEquals("/educator/expressions-of-interest", controller.expressionsOfInterest(model), "Unexpected expressions of interest endpoint");
//        assertEquals("/educator/profile", controller.profile(model), "Unexpected profile endpoint");

    }

    @Test
    void deletePost() {

    }
}