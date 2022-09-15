package com.wilma.entity.forum;

import com.wilma.entity.users.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "replies")
@Entity
public class Reply extends ForumContent {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Reply(Integer id, UserAccount author, Date timestamp, String body, Post post) {
        super(id, author, timestamp, body);
        this.post = post;
    }

    @Override
    public String toString() {
        return String.format("Post Id: %d, Reply Id: %d, Reply Body: %s", post.getId(), this.getId(), this.getBody());
    }
}