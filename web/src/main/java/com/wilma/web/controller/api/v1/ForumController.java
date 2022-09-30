package com.wilma.web.controller.api.v1;

import com.wilma.entity.Tag;
import com.wilma.entity.forum.ForumCategory;
import com.wilma.entity.forum.Post;
import com.wilma.entity.forum.Reply;
import com.wilma.service.forum.CategoryService;
import com.wilma.service.forum.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping("${api.v1.context-path}/forum")
public class ForumController implements ForumAPI {

    @Value("${spring.application.domain}${api.v1.context-path}/forum/")
    protected String domain;

    @Autowired
    protected ForumService forumService;
    @Autowired
    protected CategoryService categoryService;

    //region Posts
    @Override
    public ResponseEntity<Post> addPost(Post post) {
        var obj = (Post) forumService.add(post);
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", obj.getId())
                                .build().toUri())
                .body(obj);
    }

    @Override
    public ResponseEntity<?> getPost(Integer id) {
        return ResponseEntity.ok((Post) forumService.findById(id));
    }

    @Override
    public ResponseEntity<Collection<Post>> getAllPosts() {
        return ResponseEntity.ok(forumService.getPosts());
    }

    @Override
    public ResponseEntity<Post> updatePost(Post post) {
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", post.getId())
                                .build().toUri())
                .body((Post) forumService.update(post));
    }

    @Override
    public ResponseEntity<?> deletePostById(Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(forumService.deleteById(id));
    }
    //endregion

    //region Replies
    @Override
    public ResponseEntity<Reply> addReply(Reply reply) {
        var obj = (Reply) forumService.add(reply);
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", obj.getId())
                                .build().toUri())
                .body(obj);
    }

    @Override
    public ResponseEntity<?> getReply(Integer id) {
        return ResponseEntity.ok((Reply) forumService.findById(id));
    }

    @Override
    public ResponseEntity<Collection<Reply>> getAllReplies() {
        return ResponseEntity.ok(forumService.getReplies());
    }

    @Override
    public ResponseEntity<Reply> updateReply(Reply reply) {
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", reply.getId())
                                .build().toUri())
                .body((Reply) forumService.update(reply));
    }

    @Override
    public ResponseEntity<?> deleteReplyById(Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(forumService.deleteById(id));
    }
    //endregion

    //region Tags
    @Override
    public ResponseEntity<Tag> addTag(Tag tag) {
        var obj = (Tag) forumService.addTag(tag);
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", obj.getId())
                                .build().toUri())
                .body(obj);
    }

    @Override
    public ResponseEntity<?> getTag(Integer id) {
        return ResponseEntity.ok(forumService.findTagById(id));
    }

    @Override
    public ResponseEntity<Collection<Tag>> getAllTags() {
        return ResponseEntity.ok(forumService.findAllTags());
    }

    @Override
    public ResponseEntity<Tag> updateTag(Tag tag) {
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", tag.getId())
                                .build().toUri())
                .body(forumService.updateTag(tag));
    }

    @Override
    public ResponseEntity<?> deleteTagById(Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(forumService.deleteTagById(id));
    }
    //endregion

    //region Forum Categories
    @Override
    public ResponseEntity<ForumCategory> addForumCategory(ForumCategory category) {
        var obj = (ForumCategory) categoryService.add(category);
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", obj.getId())
                                .build().toUri())
                .body(obj);
    }

    @Override
    public ResponseEntity<?> getForumCategory(Integer id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @Override
    public ResponseEntity<Collection<ForumCategory>> getAllForumCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @Override
    public ResponseEntity<ForumCategory> updateForumCategory(ForumCategory category) {
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", category.getId())
                                .build().toUri())
                .body(categoryService.update(category));
    }

    @Override
    public ResponseEntity<?> deleteForumCategoryById(Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(categoryService.deleteById(id));
    }
    //endregion

}
