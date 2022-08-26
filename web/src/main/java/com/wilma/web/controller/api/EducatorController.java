package com.wilma.web.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/educators")
public class EducatorController {

    @GetMapping
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("Hello from the educator controller");
    }
}
