package com.wilma.entity.forum;

import com.wilma.entity.users.Partner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ForumTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void createForumContentAndAddUser() {
        var testPartner = new Partner("testbiz", "Test Business");
        var forumContent = new ForumContent(1, testPartner, new Date(), "Content");
        assertEquals( testPartner, forumContent.getAuthor() );
    }

    @Test
    void createReplyToPost() {
        var post = new Post(1,null, new Date(),"Post Title","Post message", null, null);
        var reply = new Reply(2, null, new Date(), "Reply message", post);
        assertEquals("Post Title", reply.getPost().getTitle());
    }

}