package com.walmart.ticketmaster.service.impl;

import com.walmart.ticketmaster.dao.SeatHoldDao;
import com.walmart.ticketmaster.domain.Seat;
import com.walmart.ticketmaster.domain.SeatHold;
import com.walmart.ticketmaster.exception.InvalidDataException;
import com.walmart.ticketmaster.service.SeatHoldService;
import com.walmart.ticketmaster.util.constants.SeatStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
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
    public int numSeatsAvailable(Optional<Integer> level) throws InvalidDataException {
        if (level.isPresent()) {
            validateVenueLevelId(level.get());
            return seatHoldDao.seatsAvailable(level.get(), Optional.<SeatStatusEnum>of(SeatStatusEnum.AVAILABLE));
        } else {
            return seatHoldDao.seatsAvailable(Optional.<SeatStatusEnum>of(SeatStatusEnum.AVAILABLE));
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
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

        List<Seat> holdSeats = new ArrayList<>(numSeats);
        for (int i = minLevel.get(); i <= maxLevel.get() ; i++) {
            holdSeats.addAll(seatHoldDao.findSeats(numSeats, i, Optional.<SeatStatusEnum>of(SeatStatusEnum.AVAILABLE)));
            if (holdSeats.size() == numSeats) {
                break;
            }
        }
        SeatHold seatHold = new SeatHold();
        seatHold.setEmail(customerEmail);
        seatHold.setReserved(false);
        seatHold.setHoldTime(Calendar.getInstance().getTime());
        for (Seat s : holdSeats) {
            System.out.println("Seat Num: " + s.getNum());
            s.setStatus(SeatStatusEnum.HOLD.getStatus());
        }
        seatHold.setSeats(new HashSet<>(holdSeats));
        seatHold = seatHoldDao.holdSeats(seatHold);
        return seatHold;
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
