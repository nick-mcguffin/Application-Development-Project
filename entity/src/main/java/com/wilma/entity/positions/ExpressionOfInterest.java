package com.wilma.entity.positions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "expression_of_interest")
public class ExpressionOfInterest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column
    private String category;

    @Column
    private String location;

    @Column
    private String description;
    
    @Column
    private Date date;

@Column(name = "is_filled")
    private boolean isFilled;

    public ExpressionOfInterest(Integer id, String category, String location, String description, Date date, boolean isFilled) {
        this.id = id;
        this.category = category;
        this.location = location;
        this.description = description;
        this.date = date;
        this.isFilled = isFilled;
    }
}
