package com.walmart.ticketmaster.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Durga on 12/15/2015.
 */
@Setter
@Getter
@Entity
@Table(name = "SEAT")
public class Seat {

    @Id
    private int id;

    @Column(name = "ROW_ID", nullable = false)
    private int rowId;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEVEL_ID", nullable = false)
    private Level level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POLICY_OID", nullable = false)
    private SeatHold seatHold;
}
