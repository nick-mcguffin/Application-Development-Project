package com.wilma.service.positions;

import com.wilma.entity.docs.UserDocument;
import com.wilma.entity.dto.ApplicationDTO;
import com.wilma.entity.positions.Position;
import com.wilma.entity.positions.PositionApplication;
import com.wilma.repository.PositionApplicationRepository;
import com.wilma.repository.PositionRepository;
import com.wilma.service.CrudOpsImpl;
import com.wilma.service.UserService;
import com.wilma.service.docs.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PositionService extends CrudOpsImpl<Position, Integer, PositionRepository> {

    @Autowired
    private DocumentService documentService;
    @Autowired
    private PositionApplicationRepository applicationRepository;
    @Autowired
    private UserService userService;

    /**
     * Submit an application for an available position
     * @param applicationDTO The data transfer object used to create a {@link PositionApplication}
     */
    public void submitApplicationFromDTO(ApplicationDTO applicationDTO) {
        var application = new PositionApplication(
                null,
                findById(applicationDTO.getPositionId()),
                userService.getCurrentUser(),
                getFilteredUserDocuments(applicationDTO),
                applicationDTO.getMessage(),
                false
        );
        application = applicationRepository.save(application);
        log.info("Application with id "+ application.getId() +" submitted successfully for user with id "+ application.getApplicant().getUserId());
    }

    /**
     * Filter a user's documents only keeping those with the given ids
     * @param applicationDTO The application transfer object containing the list of document ids
     * @return The filtered list of user documents
     */
    public Set<UserDocument> getFilteredUserDocuments(ApplicationDTO applicationDTO){
        return documentService.findAllForUser().stream()
                .distinct()
                .filter(doc -> applicationDTO.getFileIds().contains(doc.getId()))
                .collect(Collectors.toSet());
    }

    /**
     * Get a distinct collection of all {@link PositionApplication}s with {@link PositionApplication#isViewed()} = false
     * @return A set on un-viewed applications
     */
    public Set<PositionApplication> getAllUnViewedApplications() {
        return applicationRepository.findByViewed(false);
    }

    /**
     * Updates a collection of {@link PositionApplication}s
     * @param applications A collection of applications with unsaved changes
     * @return A collection of updated applications
     */
    public Collection<PositionApplication> updateAllApplications(Collection<PositionApplication> applications){
        return applicationRepository.saveAll(applications);
    }
}