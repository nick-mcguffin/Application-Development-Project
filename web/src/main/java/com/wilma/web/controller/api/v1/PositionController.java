package com.wilma.web.controller.api.v1;

import com.wilma.entity.positions.ExpressionOfInterest;
import com.wilma.entity.positions.Job;
import com.wilma.entity.positions.Placement;
import com.wilma.service.positions.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
    import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping("${api.v1.context-path}/positions")
public class PositionController implements PositionsAPI {

    @Value("${spring.application.domain}${api.v1.context-path}/positions/")
    protected String domain;

    @Autowired
    protected PositionService positionService;

    //region Jobs
    @Override
    public ResponseEntity<Job> addJob(Job job) {
        var obj = (Job) positionService.add(job);
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", obj.getId())
                                .build().toUri())
                .body(obj);
    }

    @Override
    public ResponseEntity<?> getJob(Integer id) {
        return ResponseEntity.ok((Job) positionService.findById(id));
    }

    @Override
    public ResponseEntity<Collection<Job>> getAllJobs() {
        return ResponseEntity.ok(positionService.getJobs());
    }

    @Override
    public ResponseEntity<Job> updateJob(Job job) {
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", job.getId())
                                .build().toUri())
                .body((Job) positionService.update(job));
    }

    @Override
    public ResponseEntity<?> deleteJobById(Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(positionService.deleteById(id));
    }
    //endregion

    //region Placements
    @Override
    public ResponseEntity<Placement> addPlacement(Placement placement) {
        var obj = (Placement) positionService.add(placement);
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", obj.getId())
                                .build().toUri())
                .body(obj);
    }

    @Override
    public ResponseEntity<?> getPlacement(Integer id) {
        return ResponseEntity.ok((Placement) positionService.findById(id));
    }

    @Override
    public ResponseEntity<Collection<Placement>> getAllPlacements() {
        return ResponseEntity.ok(positionService.getPlacements());
    }

    @Override
    public ResponseEntity<Placement> updatePlacement(Placement placement) {
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", placement.getId())
                                .build().toUri())
                .body((Placement) positionService.update(placement));
    }

    @Override
    public ResponseEntity<?> deletePlacementById(Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(positionService.deleteById(id));
    }
    //endregion

    //region Expressions of Interest

    @Override
    public ResponseEntity<ExpressionOfInterest> addExpressionOfInterest(ExpressionOfInterest expressionOfInterest) {
        var obj = (ExpressionOfInterest) positionService.addExpressionOfInterest(expressionOfInterest);
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", obj.getId())
                                .build().toUri())
                .body(obj);
    }

    @Override
    public ResponseEntity<?> getExpressionOfInterest(Integer id) {
        return ResponseEntity.ok(positionService.getExpressionOfInterestById(id));
    }

    @Override
    public ResponseEntity<Collection<ExpressionOfInterest>> getAllExpressionsOfInterest() {
        return ResponseEntity.ok(positionService.getExpressionsOfInterest());
    }

    @Override
    public ResponseEntity<ExpressionOfInterest> updateExpressionOfInterest(ExpressionOfInterest expressionOfInterest) {
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .fromUriString(domain)
                                .queryParam("id", expressionOfInterest.getId())
                                .build().toUri())
                .body(positionService.updateExpressionOfInterest(expressionOfInterest));
    }

    @Override
    public ResponseEntity<?> deleteExpressionOfInterestById(Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(positionService.deleteExpressionOfInterestById(id));
    }
    //endregion
}
