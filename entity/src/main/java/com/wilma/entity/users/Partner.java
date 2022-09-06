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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(name = "partner_positions",
            joinColumns = @JoinColumn(name = "partner_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id"))
    private Set<Position> positions = new LinkedHashSet<>();

    public Partner(String businessName) {
        this.businessName = businessName;
    }
}