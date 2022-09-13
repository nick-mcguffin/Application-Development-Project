package com.wilma.entity.positions;

import com.wilma.entity.users.Partner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Period;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "positions")
@Inheritance(strategy = InheritanceType.JOINED)
public class Position {
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

    @Transient
    private String type;
    //Category


    public Position(Integer id, Partner partner, Date startDate, Date endDate, Period period, String location, String description, boolean filled) {
        this.id = id;
        this.partner = partner;
        this.startDate = startDate;
        this.endDate = endDate;
        this.period = period;
        this.location = location;
        this.description = description;
        this.filled = filled;
        this.type = this.getClass().getSimpleName();
    }
}