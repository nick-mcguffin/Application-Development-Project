package com.wilma.entity.positions;

import com.wilma.entity.Frequency;
import com.wilma.entity.PayType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Period;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "job")
public class Job extends Position {

    @Column(name = "pay_rate")
    private double payRate;

    @Column(name = "pay_type")
    private PayType payType;

    @Column(name = "pay_frequency")
    private Frequency payFrequency;

    public Job(Integer id, Date startDate, Date endDate, Period period, String location, String description, boolean filled, double payRate, PayType payType, Frequency payFrequency) {
        super(id, startDate, endDate, period, location, description, filled);
        this.payRate = payRate;
        this.payType = payType;
        this.payFrequency = payFrequency;
    }
}