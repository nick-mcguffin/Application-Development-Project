package com.wilma.entity.positions;

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

    public Placement(Integer id, Date startDate, Date endDate, Period period, String location, String description, boolean filled, boolean completed) {
        super(id, startDate, endDate, period, location, description, filled);
        this.completed = completed;
    }
}