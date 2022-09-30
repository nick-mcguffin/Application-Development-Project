package com.wilma.web.controller.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "Files", description = "The File API")
public interface FIleAPI {

    /**
     * Upload a file to the system
     * @param file The multipart file to be uploaded
     * @param request The servlet request context
     * @return A response entity containing the uploaded file if successful, else an error message
     * @throws IOException Thrown during any saving issues
     */
    @PostMapping(consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" })
    @Operation(summary = "Add a file", description = "Add a new file", tags = {"File", "Create"})
    @ApiResponses(value = {@ApiResponse(description = "success operation")})
    ResponseEntity<?> upload(@Parameter(description = "Add a new file") @Valid @RequestParam MultipartFile file, HttpServletRequest request) throws IOException;

    /**
     * View a file
     * @param fileId The id of the file to be viewed
     * @return A response entity containing the file if present, else an error message
     * @throws IOException Thrown during any saving issues
     */
    @GetMapping("{fileId}")
    @Operation(summary = "Get a file", description = "Get a file by id", tags = {"File", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation")})
    ResponseEntity<?> view(@Parameter(description = "Get a file by id") @PathVariable Integer fileId) throws IOException;

}
