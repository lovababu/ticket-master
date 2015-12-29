package com.walmart.ticketmaster.dao;

import com.walmart.ticketmaster.domain.Seat;
import com.walmart.ticketmaster.domain.SeatHold;
import com.walmart.ticketmaster.util.constants.SeatStatusEnum;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Durga on 12/15/2015.
 */
public interface SeatHoldDao {

    int seatsAvailable(Optional<SeatStatusEnum> seatStatusEnum);

    int seatsAvailable(int venueLevel, Optional<SeatStatusEnum> seatStatusEnum);

    List<Seat> findSeats(int numSeats, int level, Optional<SeatStatusEnum> seatStatusEnum);

    SeatHold holdSeats(SeatHold seatHold, Set<Seat> holdSeats);

    SeatHold isSeatHoldExist(int seatHoldId, String email);

    boolean validateHoldTime(SeatHold seatHold);

    String reserveSeats(SeatHold seatHold);
}
