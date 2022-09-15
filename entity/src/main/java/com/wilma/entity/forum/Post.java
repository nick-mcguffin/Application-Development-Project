package com.wilma.entity.forum;

import com.wilma.entity.Tag;
import com.wilma.entity.users.UserAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "posts")
@Entity
public class Post extends ForumContent {

    @Column(name = "title")
    private String title;

    @OneToOne
    @JoinColumn(name = "category_id")
    private ForumCategory category;

    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new LinkedHashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Reply> replies = new LinkedHashSet<>();

    public Post(Integer id, UserAccount author, Date timestamp, String title, String body,  ForumCategory category, Set<Tag> tags) {
        super(id, author, timestamp, body);
        this.title = title;
        this.category = category;
        this.tags = tags;
    }

    private boolean addReply(Reply reply){
        return replies.add(reply);
    }

    private boolean removeReply(Reply reply){
        return replies.remove(reply);
    }
}