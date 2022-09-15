
package com.wilma.service;

import com.wilma.entity.Tag;
import com.wilma.entity.forum.ForumCategory;
import com.wilma.entity.forum.Post;
import com.wilma.entity.forum.Reply;
import com.wilma.entity.users.Partner;
import com.wilma.entity.users.RemoteClient;
import com.wilma.entity.users.Role;
import com.wilma.entity.users.UserAccount;
import com.wilma.repository.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Seed data only
 */
@Getter
@Setter
@Profile("dev")//Only run in dev profile
@Service
public class SeedDataService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserAccountRepository userRepository;
    @Autowired
    private RemoteClientRepository remoteClientRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ForumContentRepository forumContentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @PostConstruct
    void initUsersAndRoles() {
        initRoles();
        initUsers();
        initRemoteClients();
        initCategories();
        initTags();
        initAuthors();
        initPosts();
        initReplies();
    }

    public void initReplies(){
        //(Integer id, UserAccount author, Date timestamp, String body, Post post)
        var google = userRepository.findByUsername("google");
        var post = postRepository.findByTitleIgnoreCase("Working with Microsoft");
        var reply1 = new Reply(null, google, new Date(), "Can we come work for you?", post);
        if(!replyRepository.existsByUid(reply1.getUid())){
            replyRepository.save(reply1);
        }
    }
    public void initPosts(){
        var category1 = categoryRepository.findByNameIgnoreCase("Job FAQ");
        var category2 = categoryRepository.findByNameIgnoreCase("General Discussion");
        var tag1 = tagRepository.findByNameIgnoreCase("Remote Work");
        var tag2 = tagRepository.findByNameIgnoreCase("Java");
        var tag3 = tagRepository.findByNameIgnoreCase("UI Design");
        var posts = List.of(
                new Post(
                        null,
                        userRepository.findByUsername("microsoft"),
                        new Date(),
                        "Working with Microsoft",
                        "Working with Microsoft is now easier than ever since we implemented a hybrid work approach.\nThis means you can now work up to 75% of the week from home!",
                        category1,
                        Set.of(tag1)
                ),
                new Post(
                        null,
                        userRepository.findByUsername("translink"),
                        new Date(),
                        "Java Developers In High Demand!",
                        "Translink is constantly on the lookout for talented graduates with a keen interest in Java, give us a call to find out more",
                        category2,
                        Set.of(tag2)
                ),
                new Post(
                        null,
                        userRepository.findByUsername("google"),
                        new Date(),
                        "Calling all Java developers",
                        "Google's annual \"Code Jam\" is quickly approaching, and we're handing out generous prize money 🤓",
                        category2,
                        Set.of(tag2, tag3)
                )
        );
        posts.forEach(post -> {
            if(!postRepository.existsByUid(post.getUid())){
                postRepository.save(post);
            }
        });
    }

    public void initAuthors(){
        var authors = List.of(
                new Partner("microsoft", "Microsoft"),
                new Partner("google", "Google"),
                new Partner("oracleCorp", "Oracle"),
                new Partner("translink", "Translink")
        );
        authors.forEach(partner -> {
            if(!userRepository.existsByUsername(partner.getUsername())){
                userRepository.save(partner);
            }
        });
    }

    public void initTags(){
        var tags = List.of(
                new Tag(null, "Java"),
                new Tag(null, "JavaScript"),
                new Tag(null, "HTML"),
                new Tag(null, "CSS"),
                new Tag(null, "Python"),
                new Tag(null, "UI Design"),
                new Tag(null, "SQL"),
                new Tag(null, "Remote Work")
        );
        tags.forEach(tag -> {
            if(!tagRepository.existsByName(tag.getName())){
                tagRepository.save(tag);
            }
        });
    }

    public void initCategories(){
        var categories = List.of(
                new ForumCategory(null, "General Discussion", "Anything goes"),
                new ForumCategory(null, "Resume Q&A", "Help with your resume"),
                new ForumCategory(null, "Placement FAQ", "Frequently asked questions about placements"),
                new ForumCategory(null, "Job FAQ", "Frequently asked questions about jobs"),
                new ForumCategory(null, "Improving your feedback", "Tips to get the best feedback possible from your placement host")        );
        categories.forEach(forumCategory -> {
            if(!categoryRepository.existsByName(forumCategory.getName())){
                categoryRepository.save(forumCategory);
            }
        });
    }

    public void initRemoteClients() {
        var clients = List.of(
                new RemoteClient("Mobile App"),
                new RemoteClient("External Web UI")
        );
        clients.forEach(remoteClient -> {
            if (!remoteClientRepository.existsByName(remoteClient.getName())) {
                remoteClientRepository.save(remoteClient);
            }
        });
    }

    public void initRoles() {
        var roles = List.of(
                new Role("STUDENT"),
                new Role("PARTNER"),
                new Role("ADMIN")
        );
        roles.forEach(role -> {
            if (roleRepository.findByName(role.getName()) == null)
                roleRepository.save(role);
        });
    }

    public void initUsers() {
        var users = List.of(
                new UserAccount(
                        null,
                        "educator",
                        passwordEncoder.encode("educator"),
                        "educator@cqu.edu.au",
                        true,
                        true,
                        true,
                        true,
                        Set.of(roleRepository.findByName("ADMIN"))),
                new UserAccount(
                        null,
                        "partner",
                        passwordEncoder.encode("partner"),
                        "partner@email.com",
                        true,
                        true,
                        true,
                        true,
                        Set.of(roleRepository.findByName("PARTNER"))),
                new UserAccount(
                        null,
                        "student",
                        passwordEncoder.encode("student"),
                        "student@email.com",
                        true,
                        true,
                        true,
                        true,
                        Set.of(roleRepository.findByName("STUDENT")))
        );

        users.forEach(user -> {
            if (userRepository.findByUsername(user.getUsername()) == null) {
                userRepository.save(user);
            }
        });
    }

}

