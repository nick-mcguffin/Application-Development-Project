package com.wilma.entity.dto;

import com.wilma.entity.users.Partner;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Data
public class PlacementDTO {
    private Integer id;
    private Partner partner;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private Period period;
    private String location;
    private String description;
    private boolean filled;

    private boolean approved;

    private boolean completed;

    private String review;

    public PlacementDTO(Integer id, Partner partner, Date startDate, Date endDate, String location, String description, boolean filled, boolean approved, boolean completed,String review) {
        this.id = id;
        this.partner = partner;
        this.startDate = startDate;
        this.endDate = endDate;
        this.period = Period.between(
                startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        );
        this.location = location;
        this.description = description;
        this.filled = filled;
        this.approved = approved;
        this.completed = completed;
        this.review = review;
    }

    public PlacementDTO() {

    }
}