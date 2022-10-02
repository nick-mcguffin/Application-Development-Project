package com.wilma.entity.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ExpressionOfInterestDTO {
    private Integer id;
    private String category;
    private String location;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private boolean isFilled;
}
