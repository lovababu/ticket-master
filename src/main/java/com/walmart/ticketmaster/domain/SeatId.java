package com.walmart.ticketmaster.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Lovababu on 12/16/2015.
 */
@Embeddable
@Setter @Getter
public class SeatId implements Serializable{
    private int seatId;
    private int rowId;
}
