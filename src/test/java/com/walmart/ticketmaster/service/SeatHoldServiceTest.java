package com.walmart.ticketmaster.service;

import com.walmart.ticketmaster.config.AppConfig;
import com.walmart.ticketmaster.domain.SeatHold;
import com.walmart.ticketmaster.exception.InvalidDataException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Durga on 12/16/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class SeatHoldServiceTest {

    @Autowired
    public SeatHoldService seatHoldService;

    @Test
    public void testNumSeatsAvailable() throws InvalidDataException {
        int num = seatHoldService.numSeatsAvailable(Optional.<Integer>empty());
        assertEquals(40, num);

        num = seatHoldService.numSeatsAvailable(Optional.<Integer>of(1));
        assertEquals(10, num);
    }

    @Test
    public void testFindAndHoldSeats() throws InvalidDataException {
        SeatHold seatHold = seatHoldService
                .findAndHoldSeats(4, Optional.of(1), Optional.<Integer>of(2), "srilekha@gmail.com");
        assertNotNull(seatHold);
        assertNotNull(seatHold.getHoldId());
    }
}
