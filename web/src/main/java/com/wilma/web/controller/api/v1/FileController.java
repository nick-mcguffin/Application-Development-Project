package com.wilma.web.controller.api.v1;

import com.wilma.config.web.UserDocumentConfiguration;
import com.wilma.service.docs.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@RestController
@RequestMapping("${api.v1.context-path}/files")
public class FileController implements FIleAPI {

    @Autowired
    protected DocumentService documentService;
    @Autowired
    protected UserDocumentConfiguration documentConfiguration;


    @Override
    public ResponseEntity<?> upload(MultipartFile file, HttpServletRequest request) throws IOException {
        if(file.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("There is no file to upload");
        }
        else{
            var savedFile = documentService.uploadFile(file);
            log.info(savedFile.getFilename() +" saved by client with IP = "+ request.getRemoteAddr());
            return ResponseEntity.ok()
                    .header("Content-type", documentConfiguration.getMediaType(savedFile.getFilename()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"".concat(savedFile.getFilename()).concat("\""))
                    .body(Files.readAllBytes(Path.of(savedFile.getFilepath())));
        }
    }

    @Override
    public ResponseEntity<?> view(Integer fileId) throws IOException {
        var file = documentService.findById(fileId);
        return ResponseEntity.ok()
                .header("Content-type", documentConfiguration.getMediaType(file.getFilename()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"".concat(file.getFilename()).concat("\""))
                .body(Files.readAllBytes(Path.of(file.getFilepath())));
    }
}
