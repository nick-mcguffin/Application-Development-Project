package com.wilma.repository;

import com.wilma.entity.forum.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    boolean existsByUid(UUID uid);
}