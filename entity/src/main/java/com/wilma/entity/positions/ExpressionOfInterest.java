package com.wilma.entity.positions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.time.Period;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wilma.entity.users.Partner;

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

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    private Period period;
    private String location;
    private String description;

    private boolean filled;
    private boolean approved;

    @Transient
    private String type;
    //Category

    public ExpressionOfInterest(Integer id, Partner partner, Date startDate, Date endDate, Period period, String location, String description, boolean filled, boolean approved) {
        this.id = id;
        this.partner = partner;
        this.startDate = startDate;
        this.endDate = endDate;
        this.period = period;
        this.location = location;
        this.description = description;
        this.filled = filled;
        this.approved = approved;
        this.type = this.getClass().getSimpleName();
    }
}
