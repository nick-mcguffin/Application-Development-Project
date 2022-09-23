package com.wilma.entity.dto;

import com.wilma.entity.users.Partner;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Period;
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
}