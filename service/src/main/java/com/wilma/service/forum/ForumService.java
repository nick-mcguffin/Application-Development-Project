package com.wilma.service.forum;

import com.wilma.entity.forum.ForumContent;
import com.wilma.entity.forum.Post;
import com.wilma.entity.forum.Reply;
import com.wilma.repository.ForumContentRepository;
import com.wilma.repository.PostRepository;
import com.wilma.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    CategoryService categoryService;

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
}
