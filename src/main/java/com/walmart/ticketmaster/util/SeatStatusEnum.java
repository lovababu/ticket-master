package com.walmart.ticketmaster.util;

/**
 * Created by Durga on 12/15/2015.
 */
public enum  SeatStatusEnum {

    AVAILABLE("available"), HOLD("hold"), SOLD("sold");

    private final String status;

    SeatStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
