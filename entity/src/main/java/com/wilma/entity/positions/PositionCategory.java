package com.wilma.entity.positions;

import com.wilma.entity.users.Partner;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Period;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "position_category")
public class PositionCategory extends Position {
    
    @Column(name = "category_name")
    private String categoryName;

    public PositionCategory(Integer id, Partner partner, Date startDate, Date endDate, Period period, String location, String description, boolean filled, boolean approved, String categoryName){
        super(id, partner, startDate, endDate, period, location, description, filled, approved);
        this.categoryName = categoryName;
    }
}
