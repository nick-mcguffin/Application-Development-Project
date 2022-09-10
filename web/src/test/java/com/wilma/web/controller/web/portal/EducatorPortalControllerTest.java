package com.wilma.web.controller.web.portal;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EducatorPortalControllerTest {

    @Test
    void testEducatorPortalEndpoints() {
        var controller = new EducatorPortalController();
        var model = new ConcurrentModel();
        assertEquals("/educator/dashboard", controller.dashboard(model), "Unexpected dashboard endpoint");
        assertEquals("/educator/marketplace", controller.marketplace(model), "Unexpected marketplace endpoint");
        assertEquals("/educator/forum/overview", controller.forumOverview(model), "Unexpected forum endpoint");
        assertEquals("/educator/forum/forum-content", controller.forumContent("post", model), "Unexpected forum endpoint");
        assertEquals("/educator/forum/forum-thread", controller.forumThread("post", model), "Unexpected forum endpoint");
        assertEquals("/educator/expressions-of-interest", controller.expressionsOfInterest(model), "Unexpected expressions of interest endpoint");
//        assertEquals("/educator/profile", controller.profile(model), "Unexpected profile endpoint");
    }
}