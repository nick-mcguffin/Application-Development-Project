package com.wilma.service;

import com.wilma.entity.users.Educator;
import com.wilma.entity.users.UserAccount;
import com.wilma.repository.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService extends CrudOpsImpl<UserAccount, Integer, UserAccountRepository> {

    @Value("${spring.profiles.active}")
    private String activeProfile;
    @Autowired
    private UserAccountRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserAccount add(UserAccount userAccount){

        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));

        return userRepository.save(userAccount);
    }

    public UserAccount getCurrentUser(){
        return activeProfile.equalsIgnoreCase("prod")?
                this.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()) :
                this.findByUsername("educator");
    }

    public UserAccount findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Educator updateEducatorProfile(Educator updatedEducator) {
        var currentUser = (Educator) this.getCurrentUser();
        currentUser.setUsername(updatedEducator.getUsername());
        currentUser.setDiscipline(updatedEducator.getDiscipline());
        currentUser.setBio(updatedEducator.getBio());
        return userRepository.save(currentUser);
    }
}
