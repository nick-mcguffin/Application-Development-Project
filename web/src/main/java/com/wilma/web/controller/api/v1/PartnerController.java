package com.wilma.web.controller.api.v1;

import com.wilma.config.web.UserDocumentConfiguration;
import com.wilma.entity.users.Partner;
import com.wilma.service.UserService;
import com.wilma.service.docs.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping("${api.v1.context-path}/partners")
public class PartnerController implements PartnerAPI {

    @Value("${spring.application.domain}${api.v1.context-path}/partners/")
    protected String domain;

    @Autowired
    protected UserService userService;
    @Autowired
    protected DocumentService documentService;
    @Autowired
    protected UserDocumentConfiguration documentConfiguration;

    @Override
    public ResponseEntity<Partner> add(Partner partner) {
        var obj = (Partner) userService.add(partner);
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", obj.getUserId())
                                .build().toUri())
                .body(obj);
    }

    @Override
    public ResponseEntity<?> get(Integer id, String username) {
        if (id != null) return ResponseEntity.ok((Partner) userService.findById(id));
        else if (!username.isEmpty()) return ResponseEntity.ok((Partner) userService.findByUsername(username));
        else return ResponseEntity.badRequest().body("No ID or username was detected");
    }

    @Override
    public ResponseEntity<Collection<Partner>> getAll() {
        return ResponseEntity.ok(userService.findAllPartners());
    }

    @Override
    public ResponseEntity<Partner> update(Partner partner) {
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", partner.getUserId())
                                .build().toUri())
                .body((Partner) userService.update(partner));
    }

    @Override
    public ResponseEntity<?> deleteById(Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.deleteById(id));
    }

}
