package com.wilma.entity.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Partner extends UserAccount {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "Business_name")
    private String businessName;

    @Column(name = "profile_image_id")
    private Integer profileImageId;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "australian_business_number")
    private String abn;

    public Partner(Integer userId, String username, String password, String email, String bio, boolean credentialsNonExpired, boolean accountNonLocked, boolean accountNonExpired, boolean enabled, Set<Role> roles, String businessName) {
        super(userId, username, password, email, bio, credentialsNonExpired, accountNonLocked, accountNonExpired, enabled, roles);
        this.businessName = businessName;  
    }

    public Partner(Integer userId, String username, String password, String email, String bio, boolean credentialsNonExpired, boolean accountNonLocked, boolean accountNonExpired, boolean enabled, Set<Role> roles, String businessName,String firstName, String lastName, String contactNumber, String abn) {
        super(userId, username, password, email, bio, credentialsNonExpired, accountNonLocked, accountNonExpired, enabled, roles);
        this.businessName = businessName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.abn = abn;
    } 


    public Partner(String username, String businessName) {
        super(username);
        this.businessName = businessName;
    }
}