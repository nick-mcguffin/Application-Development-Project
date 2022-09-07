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
//        assertEquals("/educator/forum", controller.forum(model), "Unexpected forum endpoint");
//        assertEquals("/educator/expressions_of_interest", controller.expressionsOfInterest(model), "Unexpected expressions_of_interest endpoint");
//        assertEquals("/educator/profile", controller.profile(model), "Unexpected profile endpoint");
    }
}