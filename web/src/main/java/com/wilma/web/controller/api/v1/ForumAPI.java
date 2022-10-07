package com.wilma.web.controller.api.v1;

import com.wilma.entity.forum.ForumCategory;
import com.wilma.entity.forum.Post;
import com.wilma.entity.forum.Reply;
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

@Tag(name = "Forum", description = "The Forum API")
public interface ForumAPI {


    //region Posts
    @PostMapping(value = "/posts", consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" })
    @Operation(summary = "Add a post", description = "Add a new post", tags = {"Post", "Create"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))})})
    ResponseEntity<Post> addPost(@Parameter(description = "Add a new post") @Valid @RequestBody Post post);

    @GetMapping("/posts")
    @Operation(summary = "Get a post", description = "Get a post by id", tags = {"Post", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))})})
    ResponseEntity<?> getPost(@Parameter(description = "Get a post by id") @RequestParam(required = false) Integer id);

    @GetMapping("/posts/all")
    @Operation(summary = "Get all posts", description = "Get a list of all posts", tags = {"Post", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))})})
    ResponseEntity<Collection<Post>> getAllPosts();

    @PatchMapping("/posts")
    @Operation(summary = "Update a post", description = "Update an existing post", tags = {"Post", "Update"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))})})
    ResponseEntity<Post> updatePost(@Parameter(description = "The updated post object") @Valid @RequestBody Post post);

    @DeleteMapping("/posts/{id}")
    @Operation(summary = "Delete a post", description = "Delete an existing post by id", tags = {"Post", "Delete"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", responseCode = "204")})
    ResponseEntity<?> deletePostById(@Parameter(description = "The id of the post to delete") @Valid @PathVariable Integer id);
    //endregion

    //region Replies
    @PostMapping(value = "/replies", consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" })
    @Operation(summary = "Add a reply", description = "Add a new reply", tags = {"Reply", "Create"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Reply.class))})})
    ResponseEntity<Reply> addReply(@Parameter(description = "Add a new reply") @Valid @RequestBody Reply reply);

    @GetMapping("/replies")
    @Operation(summary = "Get a reply", description = "Get a reply by id", tags = {"Reply", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Reply.class))})})
    ResponseEntity<?> getReply(@Parameter(description = "Get a reply by id") @RequestParam(required = false) Integer id);

    @GetMapping("/replies/all")
    @Operation(summary = "Get all replies", description = "Get a list of all replies", tags = {"Reply", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Reply.class))})})
    ResponseEntity<Collection<Reply>> getAllReplies();

    @PatchMapping("/replies")
    @Operation(summary = "Update a reply", description = "Update an existing reply", tags = {"Reply", "Update"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Reply.class))})})
    ResponseEntity<Reply> updateReply(@Parameter(description = "The updated reply object") @Valid @RequestBody Reply reply);

    @DeleteMapping("/replies/{id}")
    @Operation(summary = "Delete a reply", description = "Delete an existing reply by id", tags = {"Reply", "Delete"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", responseCode = "204")})
    ResponseEntity<?> deleteReplyById(@Parameter(description = "The id of the reply to delete") @Valid @PathVariable Integer id);
    //endregion

    //region Tags
    @PostMapping(value = "/tags", consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" })
    @Operation(summary = "Add a tag", description = "Add a new tag", tags = {"Tag", "Create"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.wilma.entity.Tag.class))})})
    ResponseEntity<com.wilma.entity.Tag> addTag(@Parameter(description = "Add a new tag") @Valid @RequestBody com.wilma.entity.Tag tag);

    @GetMapping("/tags")
    @Operation(summary = "Get a tag", description = "Get a tag by id", tags = {"Tag", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.wilma.entity.Tag.class))})})
    ResponseEntity<?> getTag(@Parameter(description = "Get a tag by id") @RequestParam(required = false) Integer id);

    @GetMapping("/tags/all")
    @Operation(summary = "Get all tags", description = "Get a list of all tags", tags = {"Tag", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.wilma.entity.Tag.class))})})
    ResponseEntity<Collection<com.wilma.entity.Tag>> getAllTags();

    @PatchMapping("/tags")
    @Operation(summary = "Update a tag", description = "Update an existing tag", tags = {"Tag", "Update"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.wilma.entity.Tag.class))})})
    ResponseEntity<com.wilma.entity.Tag> updateTag(@Parameter(description = "The updated tag") @Valid @RequestBody com.wilma.entity.Tag tag);

    @DeleteMapping("/tags/{id}")
    @Operation(summary = "Delete a tag", description = "Delete an existing tag by id", tags = {"Tag", "Delete"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", responseCode = "204")})
    ResponseEntity<?> deleteTagById(@Parameter(description = "The id of the tag to delete") @Valid @PathVariable Integer id);
    //endregion

    //region Forum Category
    @PostMapping(value = "/categories", consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" })
    @Operation(summary = "Add a category", description = "Add a new category", tags = {"Category", "Create"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ForumCategory.class))})})
    ResponseEntity<ForumCategory> addForumCategory(@Parameter(description = "Add a new category") @Valid @RequestBody ForumCategory category);

    @GetMapping("/categories")
    @Operation(summary = "Get a category", description = "Get a category by id", tags = {"Category", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ForumCategory.class))})})
    ResponseEntity<?> getForumCategory(@Parameter(description = "Get a category by id") @RequestParam(required = false) Integer id);

    @GetMapping("/categories/all")
    @Operation(summary = "Get all categories", description = "Get a list of all categories", tags = {"Category", "Get"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ForumCategory.class))})})
    ResponseEntity<Collection<ForumCategory>> getAllForumCategories();

    @PatchMapping("/categories")
    @Operation(summary = "Update a category", description = "Update an existing category", tags = {"Category", "Update"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ForumCategory.class))})})
    ResponseEntity<ForumCategory> updateForumCategory(@Parameter(description = "The updated category object") @Valid @RequestBody ForumCategory category);

    @DeleteMapping("/categories/{id}")
    @Operation(summary = "Delete a category", description = "Delete an existing category by id", tags = {"Category", "Delete"})
    @ApiResponses(value = {@ApiResponse(description = "success operation", responseCode = "204")})
    ResponseEntity<?> deleteForumCategoryById(@Parameter(description = "The id of the category to delete") @Valid @PathVariable Integer id);
    //endregion

}
