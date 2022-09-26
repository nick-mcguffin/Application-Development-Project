package com.wilma.entity.positions;

import com.wilma.entity.docs.UserDocument;
import com.wilma.entity.users.UserAccount;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "position_application")
public class PositionApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private UserAccount applicant;

    @ManyToMany
    @JoinTable(
            name = "application_documents",
            joinColumns = @JoinColumn(name = "application_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    @ToString.Exclude
    private Set<UserDocument> userDocuments = new LinkedHashSet<>();

    @Column(length = 4096)
    private String message;

    private boolean viewed;
}