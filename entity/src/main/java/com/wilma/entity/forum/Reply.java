package com.wilma.entity.forum;

import com.wilma.entity.users.UserAccount;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reply")
public class Reply extends ForumContent {

    @ManyToOne
    private Post post;

    public Reply(Integer id, UserAccount author, Date timestamp, String body, Post post) {
        super(id, author, timestamp, body);
        this.post = post;
    }
}