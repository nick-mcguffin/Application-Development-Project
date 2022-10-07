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


    /**
     * Returns the {@link Period} between the placement start and end dates (if provided, zero days otherwise) by first
     * <a href="https://stackoverflow.com/questions/21242110/convert-java-util-date-to-java-time-localdate/21242111#21242111">converting the Date objects into LocalDate objects</a>
     * @return the duration between the start and end dates <b>plus one day</b>
     */
    public Period getPeriod() {
        if (startDate != null && endDate != null) {
            this.period = Period.between(
                    startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            )
            .plusDays(1);
        }
        else this.period = Period.ZERO;
        return this.period;
    }
}