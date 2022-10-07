package com.wilma.web.controller.api.v1;

import com.wilma.entity.users.Educator;
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

@Tag(name = "Educator", description = "The Educator API")
public interface EducatorAPI {

    @PostMapping(consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" })
    @Operation(summary = "Add an educator", description = "Add a new educator", tags = {"Educator", "Create"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Educator.class))})})
    ResponseEntity<Educator> add(@Parameter(description = "Add a new educator") @Valid @RequestBody Educator educator);

    @GetMapping
    @Operation(summary = "Get an educator", description = "Get an educator by id or username", tags = {"Educator", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Educator.class))})})
    ResponseEntity<?> get(@Parameter(description = "Get an educator by id") @RequestParam(required = false) Integer id,
                          @Parameter(description = "Get an educator by username") @RequestParam(required = false) String username);

    @GetMapping("all")
    @Operation(summary = "Get all educators", description = "Get a list of all educators", tags = {"Educator", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Educator.class))})})
    ResponseEntity<Collection<Educator>> getAll();

    @PatchMapping
    @Operation(summary = "Update an educator", description = "Update an existing educator", tags = {"Educator", "Update"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Educator.class))})})
    ResponseEntity<Educator> update(@Parameter(description = "The updated educator object") @Valid @RequestBody Educator educator);

    @DeleteMapping("{id}")
    @Operation(summary = "Delete an educator", description = "Delete an existing educator by id", tags = {"Educator", "Delete"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", responseCode = "204")})
    ResponseEntity<?> deleteById(@Parameter(description = "The id of the educator to delete") @Valid @PathVariable Integer id);
}
