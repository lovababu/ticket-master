package com.walmart.ticketmaster.dao.impl;

import com.walmart.ticketmaster.dao.SeatHoldDao;
import com.walmart.ticketmaster.domain.SeatHold;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.SQLException;

/**
 * Created by Durga on 12/15/2015.
 */
@Repository
public class SeatHoldDaoImpl implements SeatHoldDao {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public int seatsAvailable() {
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("select count(s.num) From Seat s where s.status = :status");
        query.setString("status", "Available");
        return (Integer)query.uniqueResult();
    }

    @Override
    public int seatsAvailable(Integer venueLevel) {
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery("select count(num) from SEAT where STATUS =:status and LEVEL_ID=:levelId");
        sqlQuery.setString("status", "Available");
        sqlQuery.setInteger("levelId", venueLevel);
        BigInteger seats = (BigInteger) sqlQuery.uniqueResult();
        return seats.intValue();
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
