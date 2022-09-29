package com.wilma.service.positions;

import com.wilma.entity.docs.UserDocument;
import com.wilma.entity.dto.ApplicationDTO;
import com.wilma.entity.dto.JobDTO;
import com.wilma.entity.dto.PlacementDTO;
import com.wilma.entity.positions.ExpressionOfInterest;
import com.wilma.entity.positions.Job;
import com.wilma.entity.positions.Placement;
import com.wilma.entity.positions.Position;
import com.wilma.entity.positions.PositionApplication;
import com.wilma.entity.users.Partner;
import com.wilma.repository.ExpressionOfInterestRepository;
import com.wilma.repository.JobRepository;
import com.wilma.repository.PlacementRepository;
import com.wilma.repository.PositionApplicationRepository;
import com.wilma.repository.PositionRepository;
import com.wilma.service.CrudOpsImpl;
import com.wilma.service.UserService;
import com.wilma.service.docs.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.List;
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

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    PlacementRepository placementRepository;

    @Autowired
    private ExpressionOfInterestRepository expressionOfInterestRepository;

    public Job addJobFromDTO(JobDTO jobDTO) {
        var job = new Job(null, (Partner) userService.getCurrentUser(), jobDTO.getStartDate(), jobDTO.getEndDate(), jobDTO.getPeriod(), jobDTO.getLocation(), jobDTO.getDescription(), false, false, jobDTO.getPayRate(), jobDTO.getPayType(), jobDTO.getPayFrequency());

        return jobRepository.save(job);
    }

    public Job updateJobFromDTO(JobDTO jobDTO) {
        var job = (Job) findById(jobDTO.getId());
        job.setStartDate(jobDTO.getStartDate());
        job.setEndDate(jobDTO.getEndDate());
        job.setPeriod(jobDTO.getPeriod());
        job.setLocation(jobDTO.getLocation());
        job.setDescription(jobDTO.getDescription());
        job.setFilled(jobDTO.isFilled());
        job.setApproved(jobDTO.isApproved());
        job.setPayRate(jobDTO.getPayRate());
        job.setPayType(jobDTO.getPayType());
        job.setPayFrequency(jobDTO.getPayFrequency());

        return jobRepository.save(job);
    }

    public List<Job> getJobs(){
        return jobRepository.findAll().stream()
            .filter(pos -> pos instanceof Job)
            .collect(Collectors.toList());
    }

    public Placement addPlacementFromDTO(PlacementDTO placementDTO) {
        var placement = new Placement(null,
                (Partner) userService.getCurrentUser(), placementDTO.getStartDate(), placementDTO.getEndDate(), placementDTO.getPeriod(), placementDTO.getLocation(), placementDTO.getDescription(), false, false, false);
        return placementRepository.save(placement);
    }

    public List<Placement> getPlacements(){
        return placementRepository.findAll().stream()
            .filter(pos -> pos instanceof Placement)
            .collect(Collectors.toList());
    }

    public List<ExpressionOfInterest> getExpressionsOfInterest() {
        return expressionOfInterestRepository.findAll().stream()
                .filter(pos -> pos instanceof ExpressionOfInterest)
                .collect(Collectors.toList());
    }

    public Placement updatePlacementFromDTO(PlacementDTO placementDTO) {
        var placement = (Placement) findById(placementDTO.getId());
        placement.setStartDate(placementDTO.getStartDate());
        placement.setEndDate(placementDTO.getEndDate());
        placement.setPeriod(placementDTO.getPeriod());
        placement.setLocation(placementDTO.getLocation());
        placement.setDescription(placementDTO.getDescription());
        placement.setFilled(placementDTO.isFilled());
        placement.setApproved(placementDTO.isApproved());
        placement.setCompleted(placementDTO.isCompleted());
        return placementRepository.save(placement);
    }

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

    public Object getApprovedPositions() {

        return positionRepository.findAll().stream()
                .filter(pos -> pos.isApproved())
                .collect(Collectors.toList());
    }

    public Object getPendingPositions() {
        return positionRepository.findAll().stream()
                .filter(pos -> !pos.isApproved())
                .collect(Collectors.toList());
    }
}