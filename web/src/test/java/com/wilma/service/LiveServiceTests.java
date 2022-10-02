package com.wilma.service;

import com.wilma.repository.UserDocumentRepository;
import com.wilma.service.docs.DocumentService;
import com.wilma.web.App;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = App.class)
class LiveServiceTests {

    /*
    This test is live, please limit tests here
     */

    @Mock
    private UserDocumentRepository userDocumentRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    @Autowired
    DocumentService documentService;

    @Test
    @Disabled
    void findAllForUser() {
        var userDocs = documentService.findAllForUser();
        assertNotNull(userDocs);
    }


}