package com.walmart.ticketmaster.dao.impl;

import com.walmart.ticketmaster.dao.SeatHoldDao;
import com.walmart.ticketmaster.domain.Seat;
import com.walmart.ticketmaster.domain.SeatHold;
import com.walmart.ticketmaster.util.constants.SeatStatusEnum;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Created by Durga on 12/15/2015.
 */
@Repository
public class SeatHoldDaoImpl implements SeatHoldDao {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public int seatsAvailable(Optional<SeatStatusEnum> seatStatusEnum) {
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("select count(s.num) From SEAT s where s.STATUS = :status");
        query.setString("status",
                seatStatusEnum.isPresent() ? seatStatusEnum.get().getStatus() : SeatStatusEnum.AVAILABLE.getStatus());
        return Integer.valueOf(query.uniqueResult().toString());
    }

    @Override
    public int seatsAvailable(int venueLevel, Optional<SeatStatusEnum> seatStatusEnum) {
        SQLQuery sqlQuery = sessionFactory.getCurrentSession()
                .createSQLQuery("select count(s.num) from SEAT s where s.STATUS =:status and LEVEL_ID=:levelId");
        sqlQuery.setString("status",
                seatStatusEnum.isPresent() ? seatStatusEnum.get().getStatus() : SeatStatusEnum.AVAILABLE.getStatus());
        sqlQuery.setInteger("levelId", venueLevel);
        BigInteger seats = (BigInteger) sqlQuery.uniqueResult();
        return seats.intValue();
    }

    //select rownum(), * from(select * from test order by id desc) where  rownum() < 3 and rownum() > 1;
    //SELECT ID FROM (SELECT T.*, ROWNUM AS R FROM TEST T) WHERE R BETWEEN 2 AND 3;
    @Override
    public List<Seat> findSeats(int numSeats, int level, Optional<SeatStatusEnum> seatStatusEnum) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Seat.class, "seat");
        criteria.add(Restrictions.eq("seat.level.id", level));
        criteria.add(Restrictions.eq("seat.status", seatStatusEnum.isPresent()
                ? seatStatusEnum.get().getStatus() : SeatStatusEnum.AVAILABLE.getStatus()));
        criteria.setFirstResult(0);
        criteria.setMaxResults(4);
        System.out.println("SeatHoldDaoImpl.findSeats" + criteria.toString());
        return criteria.list();
    }

    @Override
    public SeatHold holdSeats(SeatHold seatHold) {
        sessionFactory.getCurrentSession().saveOrUpdate(seatHold);
        return seatHold;
    }


    @Override
    public String reserveSeats(SeatHold seatHold) {
        SQLQuery sqlQuery = sessionFactory.getCurrentSession()
                .createSQLQuery("select seatHold from SEAT_HOLD seatHold where " +
                        "seatHold.HOLD_ID = :holdId and seatHold.EMAIL = :email");
        sqlQuery.setInteger("holdId", seatHold.getHoldId());
        sqlQuery.setString("email", seatHold.getEmail());
        seatHold = (SeatHold) sqlQuery.uniqueResult();
        if (seatHold != null) {
            int count = 0;
            for (Seat seat : seatHold.getSeats()) {
                seat.setStatus(SeatStatusEnum.SOLD.getStatus());
                sessionFactory.getCurrentSession().saveOrUpdate(seat);
                count++;
                if (count % 5 == 0) {
                    sessionFactory.getCurrentSession().flush();
                    sessionFactory.getCurrentSession().clear();
                }
            }
            seatHold.setReserved(true);
            sessionFactory.getCurrentSession().saveOrUpdate(seatHold);
        } else {
            return "Invalid Combination of SeatHoldId and Email.";
        }
        return "Your seats has been reserved, thanks for choosing us.";
    }
}
