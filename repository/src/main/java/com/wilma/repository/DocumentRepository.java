package com.wilma.repository;

import com.wilma.entity.docs.Document;
import com.wilma.entity.users.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    boolean existsByFilepath(String filepath);

    @Query("select d from Document d where d.userAccount = ?1")
    List<Document> findAllByUser(UserAccount userAccount);
}