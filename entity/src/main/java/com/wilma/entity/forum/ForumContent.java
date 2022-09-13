package com.wilma.entity.forum;

import com.wilma.entity.users.UserAccount;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "forum_content")
@Inheritance(strategy = InheritanceType.JOINED)
public class ForumContent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    private final UUID uid = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserAccount author;

    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "body", length = 4000)
    private String body;

}