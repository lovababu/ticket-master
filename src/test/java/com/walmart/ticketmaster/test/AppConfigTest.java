package com.walmart.ticketmaster.test;

import com.walmart.ticketmaster.config.AppConfig;
import com.walmart.ticketmaster.service.SeatHoldService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Durga on 12/15/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class AppConfigTest {

    @Autowired
    private SeatHoldService seatHoldService;

    @Test
    public void testAppConfig() {
        assertNotNull(seatHoldService);
    }
}


