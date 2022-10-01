package com.wilma.service.positions;

import com.wilma.entity.docs.UserDocument;
import com.wilma.entity.dto.*;
import com.wilma.entity.positions.*;
import com.wilma.entity.users.Partner;
import com.wilma.repository.*;
import com.wilma.service.CrudOpsImpl;
import com.wilma.service.UserService;
import com.wilma.service.docs.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
    protected PlacementRepository placementRepository;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    private ExpressionOfInterestRepository expressionOfInterestRepository;

    @Autowired
    protected PositionCategoryRepository positionCategoryRepository;

    @Autowired
    protected PositionApplicationRepository positionApplicationRepository;

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
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public Placement addPlacementFromDTO(PlacementDTO placementDTO) {
        var placement = new Placement(null,
                (Partner) userService.getCurrentUser(), placementDTO.getStartDate(), placementDTO.getEndDate(), placementDTO.getPeriod(), placementDTO.getLocation(), placementDTO.getDescription(), false, false, false, null);
        return placementRepository.save(placement);
    }

    public List<Placement> getPlacements(){
        return placementRepository.findAll().stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public List<ExpressionOfInterest> getExpressionsOfInterest() {
        return expressionOfInterestRepository.findAll().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public ExpressionOfInterest addExpressionOfInterest(ExpressionOfInterest eoi){
        return expressionOfInterestRepository.save(eoi);
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

    public Position setApproved(Integer id) {
        var pos = positionRepository.findById(id).orElseThrow();
        pos.setApproved(true);
        return positionRepository.save(pos);
    }

    public List<Position> pendingPositions(){
        return positionRepository.findByApproved(false);
    }

    public ExpressionOfInterest getExpressionOfInterestById(Integer id) {
        return expressionOfInterestRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public ExpressionOfInterest updateExpressionOfInterest(ExpressionOfInterest expressionOfInterest) {
        return expressionOfInterestRepository.save(expressionOfInterest);
    }

    public HttpStatus deleteExpressionOfInterestById(Integer id) {
        if(expressionOfInterestRepository.existsById(id)){
            expressionOfInterestRepository.deleteById(id);
            return HttpStatus.NO_CONTENT;//Deleted
        }
        return HttpStatus.BAD_REQUEST;// Not deleted
    }

    public PositionCategory addPositionCategory(PositionCategory category) {
        return positionCategoryRepository.save(category);
    }

    public PositionCategory getPositionCategoryById(Integer id) {
        return positionCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<PositionCategory> getPositionCategories() {
        return positionCategoryRepository.findAll().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public PositionCategory updatePositionCategory(PositionCategory category) {
        return positionCategoryRepository.save(category);
    }

    public HttpStatus deletePositionCategoryById(Integer id) {
        if(positionCategoryRepository.existsById(id)){
            positionCategoryRepository.deleteById(id);
            return HttpStatus.NO_CONTENT;//Deleted
        }
        return HttpStatus.BAD_REQUEST;// Not deleted
    }

    public PositionApplication addPositionApplication(PositionApplication application) {
        return positionApplicationRepository.save(application);
    }

    public PositionApplication getPositionApplicationById(Integer id) {
        return positionApplicationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<PositionApplication> getPositionApplications() {
        return positionApplicationRepository.findAll();
    }

    public PositionApplication updatePositionApplication(PositionApplication application) {
        return positionApplicationRepository.save(application);
    }

    public HttpStatus deletePositionApplicationById(Integer id) {
        if(positionApplicationRepository.existsById(id)){
            positionApplicationRepository.deleteById(id);
            return HttpStatus.NO_CONTENT;//Deleted
        }
        return HttpStatus.BAD_REQUEST;// Not deleted
    }
    public Object getApprovedPositions() {

        return positionRepository.findAll().stream()
                .filter(Position::isApproved)
                .collect(Collectors.toList());
    }

    public Object getPendingPositions() {
        return positionRepository.findAll().stream()
                .filter(pos -> !pos.isApproved())
                .collect(Collectors.toList());
    }


    public ExpressionOfInterest addEOIFromDTO(ExpressionOfInterestDTO eoiDTO) {
        var eoi = new ExpressionOfInterest(null, eoiDTO.getCategory(), eoiDTO.getLocation(), eoiDTO.getDescription(), eoiDTO.getDate(), false);
        return expressionOfInterestRepository.save(eoi);
    }

    public ExpressionOfInterest updateEOIFromDTO(ExpressionOfInterestDTO eoiDTO) {
        var eoi = new ExpressionOfInterest(null, eoiDTO.getCategory(), eoiDTO.getLocation(), eoiDTO.getDescription(), eoiDTO.getDate(), false);
        return expressionOfInterestRepository.save(eoi);
    }
}
}