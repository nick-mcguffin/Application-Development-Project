package com.wilma.web.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @GetMapping
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("Hello from the student controller");
    }
}