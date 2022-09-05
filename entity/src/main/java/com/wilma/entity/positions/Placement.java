package com.wilma.entity.positions;

import com.wilma.entity.users.Partner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Period;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "placement")
public class Placement extends Position {

    private boolean completed;

    public Placement(Integer id, Partner partner, Date startDate, Date endDate, Period period, String location, String description, boolean filled, boolean completed) {
        super(id, partner, startDate, endDate, period, location, description, filled);
        this.completed = completed;
    }
}