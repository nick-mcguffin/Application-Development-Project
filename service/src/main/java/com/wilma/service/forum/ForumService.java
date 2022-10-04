package com.wilma.service.forum;

import com.wilma.entity.Tag;
import com.wilma.entity.dto.PostDTO;
import com.wilma.entity.dto.ReplyDTO;
import com.wilma.entity.forum.ForumContent;
import com.wilma.entity.forum.Post;
import com.wilma.entity.forum.Reply;
import com.wilma.repository.ForumContentRepository;
import com.wilma.repository.PostRepository;
import com.wilma.repository.ReplyRepository;
import com.wilma.repository.TagRepository;
import com.wilma.service.CrudOpsImpl;
import com.wilma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumService extends CrudOpsImpl<ForumContent, Integer, ForumContentRepository> {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    /**
     * Get a list of all forum posts
     * @return A list of posts
     */
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    /**
     * Get a list of only those posts having the given category
     * @param categoryName The category to filter the posts by
     * @return A list of posts having the given category
     */
    public List<Post> getPostByCategoryName(String categoryName) {
        var category = categoryService.findByName(categoryName);
        return postRepository.findAll().stream()
                .filter(post -> post.getCategory() == category)
                .collect(Collectors.toList());
    }

    /**
     * Get a list of only those post replies having the given category
     * @param categoryName The category to filter the replies by
     * @return A list of replies having the given category
     */
    public List<Reply> getPostRepliesByCategory(String categoryName) {
        return replyRepository.findAll().stream()
                .filter(reply -> reply.getPost().getCategory().getName().equalsIgnoreCase(categoryName))
                .collect(Collectors.toList());
    }

    /**
     * Create and return a new {@link Post} using data from the given {@link PostDTO}
     * @param postDTO The post DTO
     * @return a new Post from the given DTO
     */
    public Post addPostFromDTO(PostDTO postDTO) {
        var category = categoryService.findByName(postDTO.getCategory());
        var tags = Arrays.stream(postDTO.getTags()).map(t ->
                        tagRepository.findById(Integer.valueOf(t)).orElse(null))
                .collect(Collectors.toSet());

        var currentUser = activeProfile.equalsIgnoreCase("prod") ?
                userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()) : userService.findByUsername("educator");
        var post = new Post(null, currentUser, new Date(), postDTO.getTitle(), postDTO.getBody(), category, tags);
        return postRepository.save(post);
    }

    /**
     * Create and return a new {@link Reply} using data from the given {@link ReplyDTO}
     * @param replyDTO The reply DTO
     * @return a new Reply from the given DTO
     */
    public Reply addReplyFromDTO(ReplyDTO replyDTO) {
        var post = postRepository.findById(replyDTO.getPostId()).orElse(null);
        var currentUser = activeProfile.equalsIgnoreCase("prod") ?
                userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()) : userService.findByUsername("educator");
        var reply = new Reply(null, currentUser, new Date(), replyDTO.getBody(), post);
        return replyRepository.save(reply);
    }

    public Post getPostById(Integer postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public List<Reply> getReplies() {
        return replyRepository.findAll();
    }

    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag findTagById(Integer id) {
        return tagRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    public Tag updateTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public HttpStatus deleteTagById(Integer id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
            return HttpStatus.NO_CONTENT;//Deleted
        }
        return HttpStatus.BAD_REQUEST;// Not deleted
    }

}
