package com.wilma.config.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomAccessDeniedHandlerTest {
    @InjectMocks
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void redirectUserOnAccessDeniedException() throws IOException {
        var response = new MockHttpServletResponse();
        customAccessDeniedHandler.handle(new MockHttpServletRequest(), response, new AccessDeniedException("Access Denied"));
        assertEquals("/access-denied", response.getRedirectedUrl());
    }
}
