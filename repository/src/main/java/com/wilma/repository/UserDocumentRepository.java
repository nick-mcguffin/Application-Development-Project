package com.wilma.repository;

import com.wilma.entity.docs.UserDocument;
import com.wilma.entity.users.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDocumentRepository extends JpaRepository<UserDocument, Integer> {
    boolean existsByFilepath(String filepath);

    @Query("select d from UserDocument d where d.userAccount = ?1")
    List<UserDocument> findAllByUser(UserAccount userAccount);
}