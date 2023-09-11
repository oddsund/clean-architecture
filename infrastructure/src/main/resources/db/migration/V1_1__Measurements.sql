CREATE TABLE MEASUREMENT
(
    METERING_POINT_ID   VARCHAR NOT NULL,
    FROM_DATE           DATE    NOT NULL,
    TO_DATE             DATE    NOT NULL,
    UNIT_OF_MEASUREMENT VARCHAR NOT NULL,
    MEASUREMENT         INT     NOT NULL,

    CONSTRAINT MEASUREMENT_UNIQUENESS UNIQUE (METERING_POINT_ID, FROM_DATE),
    CONSTRAINT METERING_POINT_FK FOREIGN KEY (METERING_POINT_ID) REFERENCES METERING_POINT (METERING_POINT_ID)
)
