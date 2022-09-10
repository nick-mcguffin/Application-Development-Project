package com.wilma.entity.positions;

import javax.persistence.Entity;

import java.time.Period;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.wilma.entity.users.Partner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "expression_of_interest")
public class ExpressionOfInterest extends PositionCategory {
    
    @Column(name = "author_id")
    private Integer authorId;

    @Column(name = "category_id")
    private Integer categoryId;

    private String title;
    
    private String positionType;

    private Boolean current;

    private Integer partnerId;

    public ExpressionOfInterest(Integer id, Partner partner, Date startDate, Date endDate, Period period, String location, String description, boolean filled, boolean approved, String categoryName, String title, String positionType, Boolean current) {
        super(id, partner, startDate, endDate, period, location, description, filled, approved, categoryName);
        this.title = title;
        this.current = current;
        this.positionType = positionType;
    }

}
