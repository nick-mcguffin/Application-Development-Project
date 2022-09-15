
package com.wilma.entity.users;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * User super class
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class UserAccount {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer userId;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "credentials_non_expired")
    public boolean credentialsNonExpired;

    @Column(name = "account_non_locked")
    public boolean accountNonLocked;

    @Column(name = "account_non_expired")
    public boolean accountNonExpired;

    @Column(name = "active")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new java.util.LinkedHashSet<>();

    public UserAccount(String username) {
        this.username = username;
    }
}
