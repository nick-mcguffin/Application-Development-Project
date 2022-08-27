
package com.wilma.service;

import com.wilma.entity.forum.users.RemoteClient;
import com.wilma.entity.forum.users.Role;
import com.wilma.entity.forum.users.UserAccount;
import com.wilma.repository.RemoteClientRepository;
import com.wilma.repository.RoleRepository;
import com.wilma.repository.UserAccountRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

/**
 * Seed data only
 */
@Getter
@Setter
@Service
public class SeedDataService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserAccountRepository userRepository;
    @Autowired
    private RemoteClientRepository remoteClientRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    void initUsersAndRoles() {
        initRoles();
        initUsers();
        initRemoteClients();
    }

    public void initRemoteClients() {
        var clients = List.of(
                new RemoteClient("Mobile App"),
                new RemoteClient("External Web UI")
        );
        clients.forEach(remoteClient -> {
            if (!remoteClientRepository.existsByName(remoteClient.getName())) {
                remoteClientRepository.save(remoteClient);
            }
        });
    }

    public void initRoles() {
        var roles = List.of(
                new Role("STUDENT"),
                new Role("ADMIN")
        );
        roles.forEach(role -> {
            if (roleRepository.findByName(role.getName()) == null)
                roleRepository.save(role);
        });
    }

    public void initUsers() {
        var users = List.of(
                new UserAccount(
                        null,
                        "educator",
                        passwordEncoder.encode("educator"),
                        "educator@cqu.edu.au",
                        true,
                        true,
                        true,
                        true,
                        Set.of(roleRepository.findByName("ADMIN"))),
                new UserAccount(
                        null,
                        "student",
                        passwordEncoder.encode("student"),
                        "student@email.com",
                        true,
                        true,
                        true,
                        true,
                        Set.of(roleRepository.findByName("STUDENT")))
        );

        users.forEach(user -> {
            if (userRepository.findByUsername(user.getUsername()) == null) {
                userRepository.save(user);
            }
        });
    }

}

