package com.wilma.entity.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ExpressionOfInterestDTO {
    private Integer id;
    private String category;
    private String location;
    private String description;
    private Date date;
    private boolean isFilled;
}
