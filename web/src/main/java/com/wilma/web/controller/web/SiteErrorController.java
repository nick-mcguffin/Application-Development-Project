package com.wilma.web.controller.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SiteErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model){
        var statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(statusCode != null){
            var code = Integer.parseInt(statusCode.toString());

            model.addAttribute("message", request.getAttribute(RequestDispatcher.ERROR_MESSAGE));

            if(code == HttpStatus.BAD_REQUEST.value()){
                model.addAttribute("currentPage", "400 Bad Request");
                return "/error/400";
            }
            if(code == HttpStatus.FORBIDDEN.value()){
                model.addAttribute("currentPage", "403 Forbidden");
                return "/error/403";
            }
            if(code == HttpStatus.NOT_FOUND.value()){
                model.addAttribute("currentPage", "404 Not Found");
                return "/error/404";
            }
            if(code == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                model.addAttribute("currentPage", "500 Internal Server Error");
                return "/error/500";
            }
        }
        return "error";
    }

    @RequestMapping("/access-denied")
    public String accessDenied(HttpServletRequest request, Model model){
        model.addAttribute("currentPage", "403 Forbidden");
        model.addAttribute("message", request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
        return "/error/403";
    }
}
