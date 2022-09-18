package com.wilma.service.docs;

import com.wilma.entity.docs.Document;
import com.wilma.entity.users.UserAccount;
import com.wilma.repository.DocumentRepository;
import com.wilma.service.CrudOpsImpl;
import com.wilma.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Getter
@Setter
@Service
public class DocumentService extends CrudOpsImpl<Document, Integer, DocumentRepository> {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private UserService userService;
    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String uploadSizeLimit;

    @Value("${spring.http.multipart.upload-path}")
    private String uploadPath;

    /**
     * Find all documents for a given user
     * @return A list of documents
     */
    public List<Document> findAllForUser() {
        return documentRepository.findAllByUser(this.getCurrentUser());
    }

    /**
     * Upload a document/file to the system's file directory
     * @param file The resume, cover letter, or reference etc
     * @return The uploaded file
     * @throws IOException File input output exceptions
     */
    public Document uploadFile(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadPath.concat(Objects.requireNonNull(file.getOriginalFilename())));
        Files.write(path, bytes);
        return add(new Document(null, new Date(), file.getOriginalFilename(), path.toString(), this.getCurrentUser()));
    }

    /**
     * Delete the given document/file
     * @param file The file to be deleted
     */
    public void deleteFile(Document file) {
        documentRepository.delete(file);
    }

    /**
     * Get the currently logged-in user
     * @return The user account for the currently logged-in user
     */
    public UserAccount getCurrentUser(){
        return activeProfile.equalsIgnoreCase("prod")?
                userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()) :
                userService.findByUsername("student");
    }

}
