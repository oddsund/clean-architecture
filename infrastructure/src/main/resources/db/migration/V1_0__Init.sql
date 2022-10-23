CREATE TABLE CUSTOMER
(
    CUSTOMER_ID VARCHAR NOT NULL,
    NAME        VARCHAR NOT NULL,
    COUNTRY     VARCHAR NOT NULL,

    CONSTRAINT CUSTOMER_PK PRIMARY KEY (CUSTOMER_ID)
);

CREATE TABLE METERING_POINT
(
    METERING_POINT_ID  VARCHAR NOT NULL,
    NAME               VARCHAR NOT NULL,
    POWER_ZONE         VARCHAR NOT NULL,
    STREET             VARCHAR NOT NULL,
    ZIP                VARCHAR NOT NULL,
    CUSTOMER_ID        VARCHAR NOT NULL,

    CONSTRAINT METERING_POINT_PK PRIMARY KEY (METERING_POINT_ID),
    CONSTRAINT CUSTOMER_FK FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER (CUSTOMER_ID)
);
