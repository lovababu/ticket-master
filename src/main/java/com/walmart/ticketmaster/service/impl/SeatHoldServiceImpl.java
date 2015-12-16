package com.walmart.ticketmaster.service.impl;

import com.walmart.ticketmaster.dao.SeatHoldDao;
import com.walmart.ticketmaster.domain.SeatHold;
import com.walmart.ticketmaster.exception.InvalidDataException;
import com.walmart.ticketmaster.service.SeatHoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Durga on 12/15/2015.
 */
@Service
public class SeatHoldServiceImpl implements SeatHoldService {

    @Autowired
    private SeatHoldDao seatHoldDao;

    @Override
    @Transactional(readOnly = true)
    public int numSeatsAvailable(Optional<Integer> venueLevel) throws InvalidDataException {
        if (venueLevel.isPresent()) {
            validateVenueLevelId(venueLevel.get());
            return seatHoldDao.seatsAvailable(venueLevel.get());
        } else {
            return seatHoldDao.seatsAvailable();
        }
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail) throws InvalidDataException {
        if (minLevel.isPresent()) {
            validateVenueLevelId(minLevel.get());
        } else {
            minLevel = Optional.of(new Integer(1));
        }

        if (maxLevel.isPresent()) {
            validateVenueLevelId(maxLevel.get());
        } else {
            maxLevel = Optional.of(new Integer(4));
        }

        if (numSeats <= 0) {
            throw new InvalidDataException("Number of Seats to hold is required");
        }
        if (customerEmail.isEmpty()) {
            throw new InvalidDataException("Customer Email against whom Seats need to be held is required");
        }


        return null;
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
        if (seatHoldId <= 0 || customerEmail.isEmpty()) {
            return null;
        }
        return null;
    }

    private void validateVenueLevelId(Integer venueLevelId) throws InvalidDataException {
        if (venueLevelId < 1 || venueLevelId > 4) {
            throw new InvalidDataException("Valid values for VenueLevel are 1,2,3 or 4");
        }
    }
}
