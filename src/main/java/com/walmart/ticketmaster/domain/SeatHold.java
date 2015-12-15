package com.walmart.ticketmaster.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "SEAT_ID")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seatHold")
    private Set<Seat> seats;

}
