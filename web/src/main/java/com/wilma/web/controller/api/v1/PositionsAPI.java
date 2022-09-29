package com.wilma.web.controller.api.v1;

import com.wilma.entity.positions.ExpressionOfInterest;
import com.wilma.entity.positions.Job;
import com.wilma.entity.positions.Placement;
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

@Tag(name = "Positions", description = "The Positions API")
public interface PositionsAPI {


    //region Jobs
    @PostMapping(value = "/jobs", consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" })
    @Operation(summary = "Add a job", description = "Add a new job", tags = {"Job", "Create"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Job.class))})})
    ResponseEntity<Job> addJob(@Parameter(description = "Add a new job") @Valid @RequestBody Job job);

    @GetMapping("/jobs")
    @Operation(summary = "Get a job", description = "Get a job by id or username", tags = {"Job", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Job.class))})})
    ResponseEntity<?> getJob(@Parameter(description = "Get a job by id") @RequestParam(required = false) Integer id);

    @GetMapping("/jobs/all")
    @Operation(summary = "Get all jobs", description = "Get a list of all jobs", tags = {"Job", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Job.class))})})
    ResponseEntity<Collection<Job>> getAllJobs();

    @PatchMapping("/jobs")
    @Operation(summary = "Update a job", description = "Update an existing job", tags = {"Job", "Update"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Job.class))})})
    ResponseEntity<Job> updateJob(@Parameter(description = "The updated job object") @Valid @RequestBody Job job);

    @DeleteMapping("/jobs/{id}")
    @Operation(summary = "Delete a job", description = "Delete an existing job by id", tags = {"Job", "Delete"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", responseCode = "204")})
    ResponseEntity<?> deleteJobById(@Parameter(description = "The id of the job to delete") @Valid @PathVariable Integer id);
    //endregion

    //region Placements
    @PostMapping(value = "/placements", consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" })
    @Operation(summary = "Add a placement", description = "Add a new placement", tags = {"Placement", "Create"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Placement.class))})})
    ResponseEntity<Placement> addPlacement(@Parameter(description = "Add a new placement") @Valid @RequestBody Placement placement);

    @GetMapping("/placements")
    @Operation(summary = "Get a placement", description = "Get a placement by id or username", tags = {"Placement", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Placement.class))})})
    ResponseEntity<?> getPlacement(@Parameter(description = "Get a placement by id") @RequestParam(required = false) Integer id);

    @GetMapping("/placements/all")
    @Operation(summary = "Get all placements", description = "Get a list of all placements", tags = {"Placement", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Placement.class))})})
    ResponseEntity<Collection<Placement>> getAllPlacements();

    @PatchMapping("/placements")
    @Operation(summary = "Update a placement", description = "Update an existing placement", tags = {"Placement", "Update"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Placement.class))})})
    ResponseEntity<Placement> updatePlacement(@Parameter(description = "The updated placement object") @Valid @RequestBody Placement placement);

    @DeleteMapping("/placements/{id}")
    @Operation(summary = "Delete a placement", description = "Delete an existing placement by id", tags = {"Placement", "Delete"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", responseCode = "204")})
    ResponseEntity<?> deletePlacementById(@Parameter(description = "The id of the placement to delete") @Valid @PathVariable Integer id);
    //endregion

    //region Expressions of Interest
    @PostMapping(value = "/expressions-of-interest", consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" })
    @Operation(summary = "Add an expression of interest", description = "Add a new expression of interest", tags = {"Expression of Interest", "Create"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExpressionOfInterest.class))})})
    ResponseEntity<ExpressionOfInterest> addExpressionOfInterest(@Parameter(description = "Add a new expression of interest") @Valid @RequestBody ExpressionOfInterest expressionOfInterest);

    @GetMapping("/expressions-of-interest")
    @Operation(summary = "Get an expression of interest", description = "Get an expression of interest by id", tags = {"Expression of Interest", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExpressionOfInterest.class))})})
    ResponseEntity<?> getExpressionOfInterest(@Parameter(description = "Get an expression of interest by id") @RequestParam(required = false) Integer id);

    @GetMapping("/expressions-of-interest/all")
    @Operation(summary = "Get all expressions of interest", description = "Get a list of all expressions of interest", tags = {"Expression of Interest", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExpressionOfInterest.class))})})
    ResponseEntity<Collection<ExpressionOfInterest>> getAllExpressionsOfInterest();

    @PatchMapping("/expressions-of-interest")
    @Operation(summary = "Update an expression of interest", description = "Update an existing expression of interest", tags = {"Expression of Interest", "Update"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExpressionOfInterest.class))})})
    ResponseEntity<ExpressionOfInterest> updateExpressionOfInterest(@Parameter(description = "The updated expression of interest object") @Valid @RequestBody ExpressionOfInterest expressionOfInterest);

    @DeleteMapping("/expressions-of-interest/{id}")
    @Operation(summary = "Delete an expression of interest", description = "Delete an existing expression of interest by id", tags = {"Expression of Interest", "Delete"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", responseCode = "204")})
    ResponseEntity<?> deleteExpressionOfInterestById(@Parameter(description = "The id of the expression of interest to delete") @Valid @PathVariable Integer id);
    //endregion
}
