package com.wilma.service.forum;

import com.wilma.entity.dto.PostDTO;
import com.wilma.entity.forum.ForumContent;
import com.wilma.entity.forum.Post;
import com.wilma.entity.forum.Reply;
import com.wilma.repository.ForumContentRepository;
import com.wilma.repository.PostRepository;
import com.wilma.repository.ReplyRepository;
import com.wilma.repository.TagRepository;
import com.wilma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumService extends CrudOpsImpl<ForumContent, Integer, ForumContentRepository> {

    @Autowired
    ForumContentRepository forumContentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    public List<Post> getPosts(){
        return postRepository.findAll();
    }

    public List<Post> getPostByCategoryName(String categoryName){
        var category = categoryService.findByName(categoryName);
        return postRepository.findAll().stream()
                .filter(post -> post.getCategory() == category)
                .collect(Collectors.toList());
    }

    public List<Reply> getPostRepliesByCategory(String categoryName) {
        return replyRepository.findAll().stream()
                .filter(reply -> reply.getPost().getCategory().getName().equalsIgnoreCase(categoryName))
                .collect(Collectors.toList());
    }

    public Post addPostFromDTO(PostDTO postDTO) {
        var category = categoryService.findByName(postDTO.getCategory());
        var tags = Arrays.stream(postDTO.getTags()).map(t ->
                tagRepository.findById(Integer.valueOf(t)).orElse(null))
                .collect(Collectors.toSet());

        var currentUser = activeProfile.equalsIgnoreCase("prod")?
                userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()) : userService.findByUsername("educator");
        var post = new Post(null, currentUser, new Date(), postDTO.getTitle(), postDTO.getBody(), category, tags);
        return postRepository.save(post);
    }
}
