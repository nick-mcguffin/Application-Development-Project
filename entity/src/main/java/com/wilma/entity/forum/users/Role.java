
package com.wilma.entity.forum.users;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * User roles for defining authorities
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long roleId;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    private Set<UserAccount> users;

    public Role(String name){
        this.name = name;
    }

    public Role(Long id, String name){
        this.roleId = id;
        this.name = name;
    }
}
