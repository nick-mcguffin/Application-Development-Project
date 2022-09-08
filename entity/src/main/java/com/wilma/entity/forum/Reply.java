package com.wilma.entity.forum;

import com.wilma.entity.users.UserAccount;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
    @JoinColumn(name = "post_id")
    private Post post;

    public Reply(Integer id, UserAccount author, Date timestamp, String content, Post post) {
        super(id, author, timestamp, content);
        this.post = post;
    }
}