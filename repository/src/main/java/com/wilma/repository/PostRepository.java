package com.wilma.repository;

import com.wilma.entity.forum.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    boolean existsByUid(UUID uid);
    Post findByTitleIgnoreCase(String title);
}