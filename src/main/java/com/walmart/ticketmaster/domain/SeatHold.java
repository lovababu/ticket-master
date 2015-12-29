package com.walmart.ticketmaster.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Durga on 12/15/2015.
 */
@Setter
@Getter
@Entity
@Table(name = "SEAT_HOLD")
public class SeatHold {

    @Id
    @Column(name = "HOLD_ID")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int holdId;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "IS_RESERVED")
    @Type(type = "yes_no")
    private boolean isReserved;

    @Column(name = "HOLD_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date holdTime;

    /*@Transient
    private Set<Seat> seats;*/

}
