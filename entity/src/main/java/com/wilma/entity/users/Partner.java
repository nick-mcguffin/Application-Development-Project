package com.wilma.entity.users;

import com.wilma.entity.positions.Position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Partner extends UserAccount {

    @Column(name = "Business_name")
    private String businessName;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    private Set<Position> positions = new LinkedHashSet<>();

    public Partner(Integer userId, String username, String password, String email, boolean credentialsNonExpired, boolean accountNonLocked, boolean accountNonExpired, boolean enabled, Set<Role> roles, String businessName) {
        super(userId, username, password, email, credentialsNonExpired, accountNonLocked, accountNonExpired, enabled, roles);
        this.businessName = businessName;
    }

    public Partner(String username, String businessName) {
        super(username);
        this.businessName = businessName;
    }
}