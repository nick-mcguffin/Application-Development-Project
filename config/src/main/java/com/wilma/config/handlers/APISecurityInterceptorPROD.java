package com.wilma.config.handlers;

import com.wilma.repository.RemoteClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * Intercepts all http requests when in profile PROD
 * The preHandle method catches requests before they reach the controller
 * In this case we catch all requests starting with "/api" and validate the request's api-key header value
 */
@Slf4j
@Profile("prod")
public class APISecurityInterceptorPROD implements HandlerInterceptor {

    private final RemoteClientRepository remoteClientRepository;
    @Value("${api.unsecured-endpoints}")
    private final Set<String> unsecuredEndpoints;

    /*
    Constructor manually accepting a remote client repository (auto-wiring not supported for interceptors)
     */
    public APISecurityInterceptorPROD(RemoteClientRepository remoteClientRepository) {
        this.remoteClientRepository = remoteClientRepository;
        this.unsecuredEndpoints = Set.of("/api/v1/files");
    }

    /**
     * Intercepts all http requests BEFORE they reach the controllers.
     * Bypasses security checks if the request URI matches the set of unsecured endpoints
     * @param request The incoming http request
     * @param response The outgoing http response
     * @param handler A generic handler object
     * @return a call to the superclass' preHandle method so that the process can continue
     * @throws Exception Methods thrown by subsequent methods called from within
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var uri = request.getRequestURI();
        if(unsecuredEndpoints.stream().anyMatch(uri::startsWith)) return true;
        else if(uri.startsWith("/api")) validateAPIRequest(request);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * Validate the request by checking it has a valid API key
     * @param request The incoming API request
     * @throws AuthenticationException Thrown if the API key is invalid, incorrect, or missing
     */
    private void validateAPIRequest(HttpServletRequest request) throws AuthenticationException {
        var apiKey = request.getHeader("api-key");
        if(apiKey == null){
            log.warn("Http request from "+ request.getRemoteAddr() +" did not contain an API key");
            throw new AuthenticationException("No API key was provided");
        }
        else if (remoteClientRepository.existsByApiKey(apiKey)) {
            log.info("Successful http request from "+ request.getRemoteAddr());
        }
        else{
            log.warn("Http request from "+ request.getRemoteAddr() +" contained an invalid API key");
            throw new AuthenticationException("Invalid API key");
        }
    }
}
