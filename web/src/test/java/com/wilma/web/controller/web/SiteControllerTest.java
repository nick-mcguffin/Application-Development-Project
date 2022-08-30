package com.wilma.web.controller.web;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SiteControllerTest {

    @Test
    void testSiteWebsiteEndpoints() {
        var controller = new SiteController();
        Model model = new ConcurrentModel();
        assertEquals("/index", controller.home(model), "unexpected endpoint for 'home'");
//        assertEquals("/register", controller.register(), "unexpected endpoint for 'register'");
//        assertEquals("/about", controller.register(), "unexpected endpoint for 'about'");
//        assertEquals("/contact", controller.register(), "unexpected endpoint for 'contact'");
//        assertEquals("/login", controller.register(), "unexpected endpoint for 'login'");
    }
}