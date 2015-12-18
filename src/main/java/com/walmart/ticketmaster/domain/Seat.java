package com.walmart.ticketmaster.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by Durga on 12/15/2015.
 */
@Setter
@Getter
@Entity
@Table(name = "SEAT")
public class Seat {

    @Id
    @Column(name = "NUM")
    private int num;

    @Column(name = "ROW_ID", nullable = false)
    private int rowId;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEVEL_ID", nullable = false, referencedColumnName = "ID")
    private Level level;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SeatHold> seatHold;

    @Override
    public boolean equals(Object obj) {
        Seat inComing = (Seat) obj;
        if (this.getNum() == inComing.getNum() && this.getRowId() == inComing.getRowId()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(this.getNum()).hashCode() + Integer.valueOf(this.getRowId()).hashCode();
    }
}
