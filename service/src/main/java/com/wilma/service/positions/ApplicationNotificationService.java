package com.wilma.service.positions;

import com.wilma.entity.contact.ContactForm;
import com.wilma.entity.positions.Position;
import com.wilma.entity.positions.PositionApplication;
import com.wilma.entity.users.Partner;
import com.wilma.service.mail.Mailer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@EnableScheduling
public class ApplicationNotificationService {

    @Value("${spring.application.view-attachment-path}")
    protected String viewAttachmentPath;
    private final String NOTIFICATION_SUBJECT = "You Have New Applications";
    @Autowired
    protected Mailer mailer;
    @Autowired
    protected PositionService positionService;
    
@Value("${spring.mail.site-email}")
private String siteEmail;

    /**
     * Scheduled to run at 5:00PM each day (MON - FRI only).<br/>
     * All new applications that have not been sent to the partner (where {@link PositionApplication#isViewed()} = false)
     * are fetched, and email notifications are sent to their respective owning partners.
     */
    @Scheduled(cron = "0 0 17 ? * MON-FRI")//Runs Monday - Friday 5:00PM
    public void runScheduledEmailNotifications() {
        var unViewedApplications = positionService.getAllUnViewedApplications();
        var partnersToNotify = getPartnersToNotify(unViewedApplications);
        var mailMessages = partnersToNotify.stream().map(partner -> {
                    var message = composeMailMessage(partner, getApplicationsByPartner(unViewedApplications, partner));
                    return mailer.sendEmail(partner.getEmail(), NOTIFICATION_SUBJECT, message);
                })
                .collect(Collectors.toSet());
        log.info("Daily email notifications sent: {}", mailMessages);

        //Mark applications as viewed and update changes to the database
        unViewedApplications.forEach(application -> application.setViewed(true));
        var updatedApplications = positionService.updateAllApplications(unViewedApplications);
        log.info("Applications updated: {}", updatedApplications);
    }

    /**
     * Get all position applications that are marked as un-viewed (viewed = false). This implies that the application has not yet been
     * sent to (or viewed by) the position owner ({@link Partner})
     * @return A set of distinct {@link PositionApplication}s that have not been sent to their respective partner
     */
    public Set<PositionApplication> getNewApplications(){
        return positionService.getAllUnViewedApplications();
    }

    /**
     * Finds all unique partners that own the position from the given list of {@link PositionApplication}s
     * @param applications The set of {@link PositionApplication}s
     * @return A set of {@link Partner}s extracted from the list of applications
     */
    public Set<Partner> getPartnersToNotify(Set<PositionApplication> applications){
        return applications.stream()
                .map(application -> application.getPosition().getPartner())
                .collect(Collectors.toSet());
    }

    /**
     * Get all applications having a position owner matching the given partner.
     * @param applications The set of applications
     * @param partner The partner to search
     * @return A set of applications ONLY where {@link Position#getPartner()} equals the given partner
     */
    public Set<PositionApplication> getApplicationsByPartner(Set<PositionApplication> applications, Partner partner){
        return applications.stream()
                .filter(application -> application.getPosition().getPartner() == partner)
                .collect(Collectors.toSet());
    }

    /**
     * Composes a {@link String} message that will form the body of an email notification. For example:
     * <pre>
     *     Hi Microsoft, you have new position applications!
     *
     *      Position ID: 35
     *      Student: tommy_tester
     *      Email: tommy.tester@gmail.com
     *      Message: Here, the student would write some message to be passed on to the {@link Partner}
     *      Files:
     *       • willy-wonka.png - <a href="http://localhost:8080/api/v1/partners/view-attachment/31">http://localhost:8080/api/v1/partners/view-attachment/31</a>
     * </pre>
     * @param partner The partner who owns each position in the given applications
     * @param applications The collection of application instances
     * @return A single string message to be sent as an email to the given partner
     */
    public String composeMailMessage(Partner partner, Set<PositionApplication> applications){
        var sb = new StringBuilder();
        sb.append("Hi ").append(partner.getUsername()).append(", you have new position applications!\n\n");
        var body = ("Position ID: %d\nStudent: %s\nEmail: %s\nMessage: %s\nFiles:\n");
        applications.forEach(application -> {
            sb.append(String.format(body,
                    application.getPosition().getId(),
                    application.getApplicant().getUsername(),
                    application.getApplicant().getEmail(),
                    application.getMessage()));

            application.getUserDocuments().forEach(userDocument -> {
                sb.append("- ").append(userDocument.getFilename()).append(" - ")            //Filename
                  .append(viewAttachmentPath).append(userDocument.getId()).append("\n");    //Link to view the file online
            });
            sb.append("\n");
        });
        return new String(sb);
    }

    /**
     *  Composes and sends an email to the wilma dev address containing Contact Us form information.
     * <pre>
     *      From: Test User
     * 
     *      Email: test@email.com
     * 
     *      Details: This email works as expected.
     * </pre
     * 
     * @param contactForm The details from the contact form entered by the user.
     * 
     */
    public void sendContactUsEmail(ContactForm contactForm) {

        var sb = new StringBuilder();
        var body = ("\nFrom:  %s\n\nContact Email: %s\n\nMessage: %s");
        final var SUBJECT = "Contact Us Request Received";
        sb.append(String.format(body,
                contactForm.getName(),
                contactForm.getEmail(),
                contactForm.getDetails()));
        
Thread newThread = new Thread(() -> mailer.sendEmail(siteEmail, SUBJECT, sb.toString()));
        newThread.start();

    }
}
