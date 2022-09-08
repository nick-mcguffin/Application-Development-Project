package com.wilma.service.forum;

import com.wilma.entity.forum.ForumContent;
import com.wilma.entity.forum.Post;
import com.wilma.repository.ForumContentRepository;
import com.wilma.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService extends CrudOpsImpl<ForumContent, Integer, ForumContentRepository> {

    @Autowired
    ForumContentRepository forumContentRepository;
    @Autowired
    PostRepository postRepository;

    public List<Post> getPosts(){
        return postRepository.findAll();
    }
}
