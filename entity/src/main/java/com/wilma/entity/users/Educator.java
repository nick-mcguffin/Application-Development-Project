package com.wilma.entity.users;

import com.wilma.entity.docs.UserDocument;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.Set;

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

    public Educator(String username, String discipline) {
        super(username);
        this.discipline = discipline;
    }

    public Educator(Integer userId, String username, String password, String email, String bio, boolean credentialsNonExpired, boolean accountNonLocked, boolean accountNonExpired, boolean enabled, Set<Role> roles, Collection<UserDocument> userDocuments, String discipline, String staffId) {
        super(userId, username, password, email, bio, credentialsNonExpired, accountNonLocked, accountNonExpired, enabled, roles, userDocuments);
        this.discipline = discipline;
        this.staffId = staffId;
    }

}