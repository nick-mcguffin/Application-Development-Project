package com.wilma.entity.forum;

import com.wilma.entity.Category;
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
@Entity
public class Post extends ForumContent {

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> postTags = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Reply> replies = new LinkedHashSet<>();

    public Post(Integer id, UserAccount author, Date timestamp, String title, String body,  Category category, Set<Tag> postTags) {
        super(id, author, timestamp, body);
        this.title = title;
        this.category = category;
        this.postTags = postTags;
    }

}