package com.wilma.service;

import com.wilma.entity.users.Educator;
import com.wilma.entity.users.Partner;
import com.wilma.entity.users.Student;
import com.wilma.entity.users.UserAccount;
import com.wilma.repository.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class UserService extends CrudOpsImpl<UserAccount, Integer, UserAccountRepository> {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${spring.profiles.dev-username}")
    private String currentDevUser;
    @Autowired
    private UserAccountRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserAccount add(UserAccount userAccount){

        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));

        return userRepository.save(userAccount);
    }

    public UserAccount getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken)
            return this.findByUsername(Objects.requireNonNull(currentDevUser));
        return this.findByUsername(authentication.getName());
    }

    public UserAccount findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void updateEducatorProfile(Educator updatedEducator, Integer profileImageId) {
        var currentUser = (Educator) this.getCurrentUser();
        currentUser.setUsername(updatedEducator.getUsername());
        currentUser.setDiscipline(updatedEducator.getDiscipline());
        currentUser.setBio(updatedEducator.getBio());
        currentUser.setProfileImageId(profileImageId);
        currentUser = userRepository.save(currentUser);
        log.info("User updated: {}", currentUser);
    }

    public void updatePartnerProfile(Partner updatedPartner, Integer profileImageId) {
        var currentUser = (Partner) this.getCurrentUser();
        currentUser.setUsername(updatedPartner.getUsername());
        currentUser.setEmail(updatedPartner.getEmail());
        currentUser.setBio(updatedPartner.getBio());
        currentUser.setBusinessName(updatedPartner.getBusinessName());
        currentUser.setProfileImageId(profileImageId);
        currentUser = userRepository.save(currentUser);
        log.info("User updated: {}", currentUser);
    }

    public void updateStudentProfile(Student updatedStudent, Integer profileImageId) {
        var currentUser = (Student) this.getCurrentUser();
        currentUser.setUsername(updatedStudent.getUsername());
        currentUser.setDiscipline(updatedStudent.getDiscipline());
        currentUser.setExpectedGraduationDate(updatedStudent.getExpectedGraduationDate());
        currentUser.setBio(updatedStudent.getBio());
        currentUser.setProfileImageId(profileImageId);
        currentUser = userRepository.save(currentUser);
        log.info("User updated: {}", currentUser);
    }
}
