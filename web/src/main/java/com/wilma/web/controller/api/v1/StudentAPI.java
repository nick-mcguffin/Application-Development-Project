package com.wilma.web.controller.api.v1;

import com.wilma.entity.users.Student;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Tag(name = "Student", description = "The Student API")
public interface StudentAPI {

    @PostMapping(consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" })
    @Operation(summary = "Add a student", description = "Add a new student", tags = {"Student", "Create"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))})})
    ResponseEntity<Student> add(@Parameter(description = "Add a new student") @Valid @RequestBody Student student);

    @GetMapping
    @Operation(summary = "Get a student", description = "Get a student by id or username", tags = {"Student", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))})})
    ResponseEntity<?> get(@Parameter(description = "Get a student by id") @RequestParam(required = false) Integer id,
                          @Parameter(description = "Get a student by username") @RequestParam(required = false) String username);

    @GetMapping("all")
    @Operation(summary = "Get all students", description = "Get a list of all students", tags = {"Student", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))})})
    ResponseEntity<Collection<Student>> getAll();

    @PatchMapping
    @Operation(summary = "Update a student", description = "Update an existing student", tags = {"Student", "Update"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))})})
    ResponseEntity<Student> update(@Parameter(description = "The updated student object") @Valid @RequestBody Student student);

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a student", description = "Delete an existing student by id", tags = {"Student", "Delete"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", responseCode = "204")})
    ResponseEntity<?> deleteById(@Parameter(description = "The id of the student to delete") @Valid @PathVariable Integer id);
}
