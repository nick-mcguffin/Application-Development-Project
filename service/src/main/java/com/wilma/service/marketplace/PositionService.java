package com.wilma.service.marketplace;

        import com.wilma.entity.dto.JobDTO;
        import com.wilma.entity.dto.PlacementDTO;
        import com.wilma.entity.positions.Job;
        import com.wilma.entity.positions.Placement;
        import com.wilma.entity.positions.Position;
        import com.wilma.repository.JobRepository;
        import com.wilma.repository.PlacementRepository;
        import com.wilma.repository.PositionRepository;
        import com.wilma.service.UserService;
        import com.wilma.service.forum.CrudOpsImpl;
        import org.apache.catalina.Store;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.security.core.context.SecurityContextHolder;
        import org.springframework.stereotype.Service;
        import java.util.List;

@Service
public class PositionService extends CrudOpsImpl<Position, Integer, PositionRepository> {

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    JobRepository jobRepository;
    @Autowired
    PlacementRepository placementRepository;
    @Autowired
    UserService userService;
    @Value("${spring.profiles.active}")
    private String activeProfile;

    public Job addJobFromDTO(JobDTO jobDTO) {

        var currentUser = activeProfile.equalsIgnoreCase("prod")?
                userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()) : userService.findByUsername("educator");
        var job = new Job(null, jobDTO.getPartner(), jobDTO.getStartDate(), jobDTO.getEndDate(), jobDTO.getPeriod(), jobDTO.getLocation(), jobDTO.getDescription(), false, false, jobDTO.getPayRate(), jobDTO.getPayType(), jobDTO.getPayFrequency());

        return jobRepository.save(job);
    }

    public List<Job> getJobs(){
        return jobRepository.findAll();
    }

    public Placement addPlacementFromDTO(PlacementDTO placementDTO) {
        var currentUser = activeProfile.equalsIgnoreCase("prod")?
                userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()) : userService.findByUsername("educator");
        var placement = new Placement(null, placementDTO.getPartner(), placementDTO.getStartDate(), placementDTO.getEndDate(), placementDTO.getPeriod(), placementDTO.getLocation(), placementDTO.getDescription(), false, false, false);

        return placementRepository.save(placement);
    }

    public List<Placement> getPlacements(){
        return placementRepository.findAll();
    }

    public Placement updatePlacementFromDTO(PlacementDTO placementDTO) {
        var currentUser = activeProfile.equalsIgnoreCase("prod")?
                userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()) : userService.findByUsername("educator");
        var placement = new Placement(placementDTO.getId(), placementDTO.getPartner(), placementDTO.getStartDate(), placementDTO.getEndDate(), placementDTO.getPeriod(), placementDTO.getLocation(), placementDTO.getDescription(), false, false, false);

        return placementRepository.save(placement);
    }
}