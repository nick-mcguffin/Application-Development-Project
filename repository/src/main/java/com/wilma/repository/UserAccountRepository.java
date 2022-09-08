
package com.wilma.repository;

import com.wilma.entity.users.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    UserAccount findByUsername(String username);

    UserAccount findByEmail(String email);

    boolean existsByUsername(String username);
}