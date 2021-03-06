CREATE TABLE LEVEL (ID NUMBER, NAME VARCHAR2(15), PRICE NUMBER);

CREATE TABLE SEAT (NUM NUMBER, ROW_ID NUMBER, STATUS VARCHAR2(1), LEVEL_ID NUMBER);

CREATE TABLE SEAT_HOLD (HOLD_ID NUMBER, EMAIL VARCHAR2(30), IS_RESERVED VARCHAR2(1), HOLD_TIME TIMESTAMP);

CREATE TABLE SEAT_HOLD_ID_MAP (HOLD_ID NUMBER, SEAT_ID NUMBER);