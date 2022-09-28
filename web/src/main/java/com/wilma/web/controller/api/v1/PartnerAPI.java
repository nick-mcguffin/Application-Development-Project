package com.wilma.web.controller.api.v1;

import com.wilma.entity.users.Partner;
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

@Tag(name = "Partner", description = "The Partner API")
public interface PartnerAPI {

    @PostMapping(consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" })
    @Operation(summary = "Add a partner", description = "Add a new partner", tags = {"Partner", "Create"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Partner.class))})})
    ResponseEntity<Partner> add(@Parameter(description = "Add a new partner") @Valid @RequestBody Partner partner);

    @GetMapping
    @Operation(summary = "Get a partner", description = "Get a partner by id or username", tags = {"Partner", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Partner.class))})})
    ResponseEntity<?> get(@Parameter(description = "Get a partner by id") @RequestParam(required = false) Integer id,
                          @Parameter(description = "Get a partner by username") @RequestParam(required = false) String username);

    @GetMapping("all")
    @Operation(summary = "Get all partners", description = "Get a list of all partners", tags = {"Partner", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Partner.class))})})
    ResponseEntity<Collection<Partner>> getAll();

    @PatchMapping
    @Operation(summary = "Update a partner", description = "Update an existing partner", tags = {"Partner", "Update"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Partner.class))})})
    ResponseEntity<Partner> update(@Parameter(description = "The updated partner object") @Valid @RequestBody Partner partner);

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a partner", description = "Delete an existing partner by id", tags = {"Partner", "Delete"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", responseCode = "204")})
    ResponseEntity<?> deleteById(@Parameter(description = "The id of the partner to delete") @Valid @PathVariable Integer id);
}
