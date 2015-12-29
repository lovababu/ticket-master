package com.walmart.ticketmaster.dao.impl;

import com.walmart.ticketmaster.dao.SeatHoldDao;
import com.walmart.ticketmaster.domain.Seat;
import com.walmart.ticketmaster.domain.SeatHold;
import com.walmart.ticketmaster.util.constants.SeatStatusEnum;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
        criteria.setMaxResults(numSeats);
        System.out.println("SeatHoldDaoImpl.findSeats" + criteria.toString());
        List<Seat> seats = criteria.list();
        return seats;
    }

    @Override
    public SeatHold holdSeats(SeatHold seatHold, Set<Seat> holdSeats) {
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        Serializable id = session.save(seatHold);

        for (Seat seat : holdSeats) {
            //session.update(seat);
            SQLQuery seatUpdateQuery = session.createSQLQuery("UPDATE SEAT SET STATUS = :status WHERE NUM = :seatNum and LEVEL_ID = :levelId");
            seatUpdateQuery.setString("status", SeatStatusEnum.HOLD.getStatus());
            seatUpdateQuery.setInteger("seatNum", seat.getNum());
            seatUpdateQuery.setInteger("levelId", seat.getLevel().getId());
            int updateCnt = seatUpdateQuery.executeUpdate();
            System.out.println("No of seats updated: " + updateCnt);

            SQLQuery sqlQuery = session.createSQLQuery("INSERT INTO SEAT_HOLD_ID_MAP (HOLD_ID, SEAT_ID) VALUES (:holdId, :seatId)");
            sqlQuery.setInteger("holdId", (int) id);
            sqlQuery.setInteger("seatId", seat.getNum());
            int inserted = sqlQuery.executeUpdate();
            System.out.println("Total records inserted in SEAT_HOLD_ID_MAP is: " + inserted);
        }

        seatHold.setHoldId((int) id);
        return seatHold;
    }

    @Override
    public SeatHold isSeatHoldExist(int seatHoldId, String email) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SeatHold.class, "seatHold");
        criteria.add(Restrictions.eq("seatHold.holdId", seatHoldId))
                .add(Restrictions.eq("seatHold.email", email));
        SeatHold seatHold = (SeatHold) criteria.uniqueResult();
        return seatHold;
    }

    @Override
    public boolean validateHoldTime(SeatHold seatHold) {

        long MAX_DURATION = TimeUnit.MILLISECONDS.convert(15, TimeUnit.MINUTES);
        long duration = System.currentTimeMillis() - seatHold.getHoldTime().getTime();
        //15*60*1000
        if (duration <= MAX_DURATION) {
            return true;
        }
        return false;
    }


    @Override
    public String reserveSeats(SeatHold seatHold) {
        SQLQuery sqlQuery = sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT SEAT_ID FROM SEAT_HOLD_ID_MAP WHERE HOLD_ID = :holdId");
        sqlQuery.setInteger("holdId", seatHold.getHoldId());
        List<BigDecimal> list = (List<BigDecimal>) sqlQuery.list();
        if (!CollectionUtils.isEmpty(list)) {
            int count = 0;
            for (BigDecimal seatNum : list) {
                SQLQuery sqlQuery1 = sessionFactory.getCurrentSession().createSQLQuery("UPDATE SEAT SET STATUS = :status WHERE NUM = :seatNum");
                sqlQuery1.setString("status", SeatStatusEnum.SOLD.getStatus());
                sqlQuery1.setBigDecimal("seatNum", seatNum);
                count++;
                if (count % 5 == 0) {
                    sessionFactory.getCurrentSession().flush();
                    sessionFactory.getCurrentSession().clear();
                }
            }
            seatHold.setReserved(true);
            sessionFactory.getCurrentSession().merge(seatHold);
        }

        return "Your seats has been reserved, thanks for choosing us.";
    }
}
