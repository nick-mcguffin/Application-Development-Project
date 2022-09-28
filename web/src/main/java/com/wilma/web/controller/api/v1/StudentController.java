package com.wilma.web.controller.api.v1;

import com.wilma.entity.users.Student;
import com.wilma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping("${api.v1.context-path}/students")
public class StudentController implements StudentAPI {

    @Value("${spring.application.domain}${api.v1.context-path}/students/")
    protected String domain;

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<Student> add(Student student) {
        return ResponseEntity.ok((Student) userService.add(student));
    }

    @Override
    public ResponseEntity<?> get(Integer id, String username) {
        if (id != null) return ResponseEntity.ok((Student) userService.findById(id));
        else if (!username.isEmpty()) return ResponseEntity.ok((Student) userService.findByUsername(username));
        else return ResponseEntity.badRequest().body("No ID or username was detected");
    }

    @Override
    public ResponseEntity<Collection<Student>> getAll() {
        return ResponseEntity.ok(userService.findAllStudents());
    }

    @Override
    public ResponseEntity<Student> update(Student student) {
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", student.getUserId())
                                .build().toUri())
                .body((Student) userService.update(student));
    }

    @Override
    public ResponseEntity<?> deleteById(Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.deleteById(id));
    }
}