package com.wilma.service.positions;

import com.wilma.entity.docs.UserDocument;
import com.wilma.entity.positions.Position;
import com.wilma.entity.positions.PositionApplication;
import com.wilma.entity.users.Partner;
import com.wilma.entity.users.UserAccount;
import com.wilma.web.App;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest(classes = App.class)
class ApplicationNotificationServiceTest {

    @Mock
    PositionService positionService;
    Position position1 = new Position();
    Position position2 = new Position();
    Position position3 = new Position();
    Position position4 = new Position();
    Position position5 = new Position();
    @Mock
    UserAccount applicant1;
    Partner partner1;
    @Mock
    Partner partner2;
    @Mock
    UserAccount applicant2;
    @Mock
    Set<UserDocument> userDocuments;
    @InjectMocks
    @Autowired
    ApplicationNotificationService applicationNotificationService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        partner1 = new Partner();
        partner1.setUsername("Microsoft");
        partner2.setUsername("Google");
        position1.setPartner(partner1);
        position2.setPartner(partner2);
        position3.setPartner(partner1);
        position4.setPartner(partner2);
        position5.setPartner(partner1);
        when(positionService.getAllUnViewedApplications()).thenReturn(Set.of(
                new PositionApplication(1, position1, applicant1, userDocuments, "message 1", false),
                new PositionApplication(2, position2, applicant2, userDocuments, "message 2", false),
                new PositionApplication(3, position3, applicant1, userDocuments, "message 3", false),
                new PositionApplication(4, position4, applicant2, userDocuments, "message 4", false),
                new PositionApplication(5, position5, applicant1, userDocuments, "message 5", false)
        ));
    }

    @Test
    void getNewApplications() {
        assertEquals(5, applicationNotificationService.getNewApplications().size());
    }

    @Test
    void getApplicationsByPartner() {
        var applications = applicationNotificationService.getNewApplications();
        assertEquals(3, applicationNotificationService.getApplicationsByPartner(applications, partner1).size());
        assertEquals(2, applicationNotificationService.getApplicationsByPartner(applications, partner2).size());
    }

    @Test
    void getPartnersToNotify() {
        var applications = applicationNotificationService.getNewApplications();
        assertEquals(2, applicationNotificationService.getPartnersToNotify(applications).size());
    }

    @Test
    void composeMailMessage() {
        var applications = applicationNotificationService.getApplicationsByPartner(applicationNotificationService.getNewApplications(), partner1);
        position1.setId(23);
        position3.setId(72);
        position5.setId(124);
        applications.forEach(a -> a.setUserDocuments(Set.of(new UserDocument(31, new Date(), "willy-wonka.png", "src/main/resources/uploads/willy-wonka.png", null))));
        var mailMessage = applicationNotificationService.composeMailMessage(partner1, applications);
        assertNotNull(mailMessage);
        assertEquals(String.class, mailMessage.getClass());
        System.out.println(mailMessage);
    }

    @Disabled
    @Test
    void runScheduledEmailNotifications() {
        partner1.setEmail("aussiedev81@gmail.com");
        var applications = applicationNotificationService.getApplicationsByPartner(applicationNotificationService.getNewApplications(), partner1);
        position1.setId(23);
        position3.setId(72);
        position5.setId(124);
        applications.forEach(a -> a.setUserDocuments(Set.of(new UserDocument(31, new Date(), "willy-wonka.png", "src/main/resources/uploads/willy-wonka.png", null))));
        applicationNotificationService.runScheduledEmailNotifications();
    }
}