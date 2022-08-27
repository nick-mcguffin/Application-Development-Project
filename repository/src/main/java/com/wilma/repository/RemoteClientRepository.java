package com.wilma.repository;

import com.wilma.entity.forum.users.RemoteClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RemoteClientRepository extends JpaRepository<RemoteClient, Integer> {
    boolean existsByApiKey(String apiKey);
    boolean existsByName(String name);
}