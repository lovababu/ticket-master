package com.walmart.ticketmaster.util.constants;

/**
 * Created by Durga on 12/18/2015.
 */
public enum  LevelEnum {

    ORCHESTRA(1, 1 * 10), MAIN(2, 1 * 10), BALCONY1(3, 2 * 10), BALCONY2(4, 3 * 10);

    private final Integer level;
    private final Integer numOfSeats;


    LevelEnum(Integer level,int numOfSeats) {
        this.level = level;
        this.numOfSeats = numOfSeats;
    }

    public Integer getLevel() {
        return this.level;
    }

    public Integer getTotalSeats() {

        return numOfSeats;
    }

    @Override
    public String toString() {
        return String.valueOf(this.level);
    }
}
