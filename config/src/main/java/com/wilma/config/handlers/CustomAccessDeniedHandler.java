package com.wilma.config.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implement custom behaviour when a user visits an endpoint they do not have access to
 */
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Performs some custom action whenever a user tries to access an endpoint they are not permitted for
     *
     * @param request               The incoming http request
     * @param response              The http servlet response that will be returned to the client
     * @param accessDeniedException The exception thrown at the time when the access denied event happened
     * @throws IOException Thrown by the response redirection process if the redirect endpoint doesn't exist
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            log.warn("User: " + auth.getName()
                    + " attempted to access the protected URL: "
                    + request.getRequestURI());
        }
        response.sendRedirect(request.getContextPath() + "/access-denied");
    }

}
