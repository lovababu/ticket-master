package com.walmart.ticketmaster.dao;

import com.walmart.ticketmaster.domain.SeatHold;

/**
 * Created by Durga on 12/15/2015.
 */
public interface SeatHoldDao {

    int seatsAvailable();

    int seatsAvailable(Integer venueLevel);

    SeatHold findAndHoldSeats(SeatHold seatHold);

    String reserveSeats(int seatHoldId);
}
