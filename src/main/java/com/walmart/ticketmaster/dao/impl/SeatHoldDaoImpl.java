package com.walmart.ticketmaster.dao.impl;

import com.walmart.ticketmaster.dao.SeatHoldDao;
import com.walmart.ticketmaster.domain.SeatHold;
import org.hibernate.Query;
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
        Query query = sessionFactory.getCurrentSession().createQuery("select count(*) From Seat s where s.status = :status");
        query.setString("status", "Available");
        return (Integer)query.uniqueResult();
    }

    @Override
    public int seatsAvailable(Integer venueLevel) {
        Query query = sessionFactory.getCurrentSession().createQuery("select count(*) From Seat s where s.level.level = :levelId and s.status = :status");
        query.setInteger("levelId", venueLevel);
        query.setString("status", "Available");
        return (Integer)query.uniqueResult();
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
