package com.wilma.web.controller.api.v1;

import com.wilma.entity.users.Educator;
import com.wilma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;


@RestController
@RequestMapping("${api.v1.context-path}/educators")
public class EducatorController implements EducatorAPI {

    @Value("${spring.application.domain}${api.v1.context-path}/educators/")
    protected String domain;
    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<Educator> add(Educator educator) {
        return ResponseEntity.ok((Educator) userService.add(educator));
    }

    @Override
    public ResponseEntity<?> get(Integer id, String username) {
        if (id != null) return ResponseEntity.ok((Educator) userService.findById(id));
        else if (!username.isEmpty()) return ResponseEntity.ok((Educator) userService.findByUsername(username));
        else return ResponseEntity.badRequest().body("No ID or username was detected");
    }

    @Override
    public ResponseEntity<Collection<Educator>> getAll() {
        return ResponseEntity.ok(userService.findAllEducators());
    }

    @Override
    public ResponseEntity<Educator> update(@RequestBody Educator educator) {
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", educator.getUserId())
                                .build().toUri())
                .body((Educator) userService.update(educator));
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.deleteById(id));
    }


}
