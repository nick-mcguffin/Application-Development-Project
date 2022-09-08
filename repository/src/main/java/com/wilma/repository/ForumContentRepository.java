package com.wilma.repository;

import com.wilma.entity.forum.ForumContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ForumContentRepository extends JpaRepository<ForumContent, Integer> {
    boolean existsByUid(UUID uid);
}