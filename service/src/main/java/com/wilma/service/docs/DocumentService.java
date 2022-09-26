package com.wilma.service.docs;

import com.wilma.entity.docs.UserDocument;
import com.wilma.repository.UserDocumentRepository;
import com.wilma.service.CrudOpsImpl;
import com.wilma.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class DocumentService extends CrudOpsImpl<UserDocument, Integer, UserDocumentRepository> {

    @Autowired
    private UserDocumentRepository userDocumentRepository;
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
    public List<UserDocument> findAllForUser() {
        return userDocumentRepository.findAllByUser(userService.getCurrentUser());
    }

    /**
     * Upload a document/file to the system's file directory
     * @param file The resume, cover letter, or reference etc
     * @return The uploaded file
     * @throws IOException File input output exceptions
     */
    public UserDocument uploadFile(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadPath.concat(Objects.requireNonNull(file.getOriginalFilename())));
        Files.write(path, bytes);
        return add(new UserDocument(null, new Date(), file.getOriginalFilename(), path.toString(), userService.getCurrentUser()));
    }

    /**
     * Delete the given document/file
     * @param file The file to be deleted
     */
    public void deleteFile(UserDocument file) {
        userDocumentRepository.delete(file);
    }

}
