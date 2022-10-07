package com.wilma.service.positions;

import com.wilma.entity.dto.ExpressionOfInterestDTO;
import com.wilma.entity.dto.PlacementDTO;
import com.wilma.entity.positions.ExpressionOfInterest;
import com.wilma.entity.positions.Placement;
import com.wilma.entity.positions.Position;
import com.wilma.repository.*;
import com.wilma.service.CrudOpsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EOIService extends CrudOpsImpl<Position, Integer, PositionRepository> {

    @Autowired
    private ExpressionOfInterestRepository eoiRepository;
    @Autowired
    private PlacementRepository placementRepository;
    @Autowired
    private ExpressionOfInterestRepository expressionOfInterestRepository;
    public List<ExpressionOfInterest> getExpressionsOfInterest() {
        return expressionOfInterestRepository.findAll().stream()
                .filter(eoi -> !eoi.isFilled())
                .collect(Collectors.toList());
    }

    public Placement createPlacementFromEOI(PlacementDTO placementDTO) {
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

    public ExpressionOfInterest findEOIById(Integer id) {
        return expressionOfInterestRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }


    public HttpStatus deleteExpressionOfInterestById(Integer id) {
        if (expressionOfInterestRepository.existsById(id)) {
            expressionOfInterestRepository.deleteById(id);
            return HttpStatus.NO_CONTENT;//Deleted
        }
        return HttpStatus.BAD_REQUEST;// Not deleted
    }

    public ExpressionOfInterest addEOIFromDTO(ExpressionOfInterestDTO eoiDTO) {
        var eoi = new ExpressionOfInterest(null, eoiDTO.getCategory(), eoiDTO.getLocation(), eoiDTO.getDescription(), eoiDTO.getDate(), false);
        return eoiRepository.save(eoi);
    }

    public ExpressionOfInterest updateEOIFromDTO(ExpressionOfInterestDTO eoiDTO) {
        var eoi = findEOIById(eoiDTO.getId());
        eoi.setCategory(eoiDTO.getCategory());
        eoi.setLocation(eoiDTO.getLocation());
        eoi.setDescription(eoiDTO.getDescription());
        eoi.setDate(eoiDTO.getDate());
        eoi.setFilled(eoiDTO.isFilled());
        return eoiRepository.save(eoi);
    }
}