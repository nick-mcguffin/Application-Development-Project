package com.wilma.repository;

import com.wilma.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    boolean existsByName(String name);

    Tag findByNameIgnoreCase(String name);
}