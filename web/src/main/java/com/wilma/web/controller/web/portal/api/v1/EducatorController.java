package com.wilma.web.controller.web.portal.api.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Educator REST API
 */
@RestController
@RequestMapping("${api.v1.context-path}/educators")
public class EducatorController {

    @GetMapping
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("Hello from the educator API");
    }
}
