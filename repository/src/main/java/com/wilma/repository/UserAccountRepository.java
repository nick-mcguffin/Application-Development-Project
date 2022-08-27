
package com.wilma.repository;

import com.wilma.entity.forum.users.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    UserAccount findByUsername(String username);

    UserAccount findByEmail(String email);
}