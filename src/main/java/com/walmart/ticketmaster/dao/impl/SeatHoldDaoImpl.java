package com.walmart.ticketmaster.dao.impl;

import com.walmart.ticketmaster.dao.SeatHoldDao;
import com.walmart.ticketmaster.domain.SeatHold;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Durga on 12/15/2015.
 */
@Repository
public class SeatHoldDaoImpl implements SeatHoldDao {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public int seatsAvailable() {
        return 0;
    }

    @Override
    public int seatsAvailable(Integer venueLevel) {
        return 0;
    }

    @Override
    public SeatHold findAndHoldSeats(SeatHold seatHold) {
        return null;
    }

    @Override
    public String reserveSeats(int seatHoldId) {
        return null;
    }
}
