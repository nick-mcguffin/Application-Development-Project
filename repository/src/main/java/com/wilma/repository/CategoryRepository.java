package com.wilma.repository;

import com.wilma.entity.forum.ForumCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<ForumCategory, Integer> {
    boolean existsByName(String name);

    ForumCategory findByNameIgnoreCase(String name);
}