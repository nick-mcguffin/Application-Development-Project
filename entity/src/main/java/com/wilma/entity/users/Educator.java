package com.wilma.entity.users;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.wilma.entity.docs.UserDocument;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Educator extends UserAccount {

    @Column(name = "discipline")
    private String discipline;

    @Column(name = "staff_id")
    private String staffId;

    @Column(name = "profile_image_id")
    private Integer profileImageId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "contact_number")
    private String contactNumber;

    public Educator(String username, String discipline) {
        super(username);
        this.discipline = discipline;
    }

    public Educator(Integer userId, String username, String password, String email, String bio, boolean credentialsNonExpired, boolean accountNonLocked, boolean accountNonExpired, boolean enabled, Set<Role> roles, Collection<UserDocument> userDocuments, String discipline, String staffId) {
        super(userId, username, password, email, bio, credentialsNonExpired, accountNonLocked, accountNonExpired, enabled, roles, userDocuments);
        this.discipline = discipline;
        this.staffId = staffId;
    }

    public Educator(Integer userId, String username, String password, String email, String bio, boolean credentialsNonExpired, boolean accountNonLocked, boolean accountNonExpired, boolean enabled, Set<Role> roles, Collection<UserDocument> userDocuments, String discipline, String staffId, String firstName, String lastName, String contactNumber) {
        super(userId, username, password, email, bio, credentialsNonExpired, accountNonLocked, accountNonExpired, enabled, roles, userDocuments);
        this.discipline = discipline;
        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
    }



}