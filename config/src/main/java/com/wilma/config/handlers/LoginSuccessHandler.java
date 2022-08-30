package com.wilma.config.handlers;

import com.wilma.service.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * On authentication success is run each time a user successfully logs in via the web platform
     * @param request The scope servlet request
     * @param response The scope servlet response
     * @param authentication The authentication principal
     * @throws IOException Can be thrown by {@link LoginSuccessHandler#handle(HttpServletRequest, HttpServletResponse, Authentication)}
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info(String.format("User \"%s\" (Id: %d) successfully authenticated",
                ((CustomUserDetails) authentication.getPrincipal()).getUsername(),
                ((CustomUserDetails) authentication.getPrincipal()).getUser().getUserId()));
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    /**
     * Handle the request
     * @param request The scope servlet request
     * @param response The scope servlet response
     * @param authentication The authentication principal
     * @throws IOException Thrown by redirect exceptions
     */
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * Determine target post login redirection URL for the user
     * @param authentication Authentication instance for the user
     * @return The appropriate redirection endpoint based on the user's role
     */
    protected String determineTargetUrl(final Authentication authentication) {
        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_STUDENT", "/student/dashboard");
        roleTargetUrlMap.put("ROLE_PARTNER", "/partner/dashboard");
        roleTargetUrlMap.put("ROLE_ADMIN", "/educator/dashboard");

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if(roleTargetUrlMap.containsKey(authorityName)) {
                log.info(String.format("Target URL determined as %s", roleTargetUrlMap.get(authorityName)));
                return roleTargetUrlMap.get(authorityName);
            }
        }
        throw new IllegalStateException();
    }

    /**
     * Clear authentication session attributes
     * @param request The http servlet request for the current context
     */
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
