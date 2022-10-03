package com.wilma.entity.positions;

import com.wilma.entity.users.Partner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Period;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "placements")
@ToString
public class Placement extends Position {

    @Column(name = "completed")
    private boolean completed;

    @Column(name = "performance_review")
    private String review;

    @Column(name = "review_is_viewed")
    private boolean reviewViewed;
    public Placement(Integer id, Partner partner, Date startDate, Date endDate, Period period, String location, String description, boolean filled, boolean approved, boolean completed,String review) {
        super(id, partner, startDate, endDate, period, location, description, filled, approved);
        this.completed = completed;
        this.reviewViewed = completed;
    }
}