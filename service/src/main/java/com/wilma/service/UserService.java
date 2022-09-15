package com.wilma.service;

import com.wilma.entity.users.UserAccount;
import com.wilma.repository.UserAccountRepository;
import com.wilma.service.forum.CrudOpsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CrudOpsImpl<UserAccount, Integer, UserAccountRepository> {

    @Autowired
    private UserAccountRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserAccount add(UserAccount userAccount){
        //Note:
        //  This is what I was discussing in the meeting...
        //  - Leave this here
        //  - Put this in the UserAccount class
        //  - Put this in the UserAccountRepository
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));

        return userRepository.save(userAccount);
    }


    public UserAccount findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
