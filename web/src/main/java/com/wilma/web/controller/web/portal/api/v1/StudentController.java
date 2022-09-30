package com.wilma.web.controller.web.portal.api.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Student REST API
 */
@RestController
@RequestMapping("${api.v1.context-path}/students")
public class StudentController {

    @GetMapping
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("Hello from the student API");
    }
}