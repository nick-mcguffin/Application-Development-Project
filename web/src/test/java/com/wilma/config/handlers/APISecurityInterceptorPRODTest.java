package com.wilma.config.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.naming.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;

class APISecurityInterceptorPRODTest {

    @InjectMocks
    APISecurityInterceptorPROD aPISecurityInterceptorPROD;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testPreHandle() {
        var request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/educator");
        Exception exception = assertThrows( AuthenticationException.class, () -> aPISecurityInterceptorPROD.preHandle(request, new MockHttpServletResponse(), "handler"));
        assertEquals("No API key was provided", exception.getMessage());
    }

    @Test
    void testAbleToViewFilesWithoutAPIKey() throws Exception {
        var request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/files/38");
        assertTrue(aPISecurityInterceptorPROD.preHandle(request, new MockHttpServletResponse(), "handler"));
    }
}
