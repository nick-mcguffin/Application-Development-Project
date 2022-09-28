package com.wilma.web.controller.api.v1;

import com.wilma.config.web.UserDocumentConfiguration;
import com.wilma.entity.users.Partner;
import com.wilma.service.UserService;
import com.wilma.service.docs.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("${api.v1.context-path}/partners")
public class PartnerController implements PartnerAPI {

    @Value("${spring.application.domain}${api.v1.context-path}/partners/")
    protected String domain;

    @Autowired
    UserService userService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private UserDocumentConfiguration documentConfiguration;

    @GetMapping("/view-attachment/{documentId}")
    public ResponseEntity<?> viewAttachment(@PathVariable Integer documentId) throws IOException {
        var file = documentService.findById(documentId);
        return ResponseEntity.ok()
                .header("Content-type", documentConfiguration.getMediaType(file.getFilename()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"".concat(file.getFilename()).concat("\""))
                .body(Files.readAllBytes(Path.of(file.getFilepath())));
    }

    @Override
    public ResponseEntity<Partner> add(Partner partner) {
        return ResponseEntity.ok((Partner) userService.add(partner));
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
