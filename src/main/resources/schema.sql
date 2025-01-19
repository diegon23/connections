-- Optional: drop tables if they exist (for dev/testing)
DROP TABLE IF EXISTS COMMENT;
DROP TABLE IF EXISTS POST;
DROP TABLE IF EXISTS INSPIRATION;
DROP TABLE IF EXISTS LINK;
DROP TABLE IF EXISTS RSVP;
DROP TABLE IF EXISTS EVENT;
DROP TABLE IF EXISTS VENUE;
DROP TABLE IF EXISTS CREATOR;

-- Table: CREATOR
CREATE TABLE CREATOR (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255),
    PRONOUNS VARCHAR(255),
    BIO VARCHAR(255)
);

-- Table: VENUE
CREATE TABLE VENUE (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255),
    LOCATION VARCHAR(255),
    CAPACITY INT
);

-- Table: INSPIRATION
CREATE TABLE INSPIRATION (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    CREATOR_ID BIGINT,
    INSPIRATION_NAME VARCHAR(255),
    FOREIGN KEY (CREATOR_ID) REFERENCES CREATOR(ID)
);

-- Table: EVENT
CREATE TABLE EVENT (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    TITLE VARCHAR(255),
    DESCRIPTION VARCHAR(1000),
    DATE_TIME TIMESTAMP,
    VENUE_ID BIGINT,
    FOREIGN KEY (VENUE_ID) REFERENCES VENUE(ID)
);

-- Table: RSVP
CREATE TABLE RSVP (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    EVENT_ID BIGINT,
    CREATOR_ID BIGINT,
    STATUS VARCHAR(50),
    LAST_UPDATED TIMESTAMP,
    FOREIGN KEY (CREATOR_ID) REFERENCES CREATOR(ID),
    FOREIGN KEY (EVENT_ID) REFERENCES EVENT(ID)
);

-- Table: LINK
CREATE TABLE LINK (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    LINK VARCHAR(255),
    CREATOR_ID BIGINT,
    DESCRIPTION VARCHAR(500),
    FOREIGN KEY (CREATOR_ID) REFERENCES CREATOR(ID)
);

-- Table: POST
CREATE TABLE POST (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    CONTENT VARCHAR(2000),
    CREATED_AT TIMESTAMP,
    CREATED_BY VARCHAR(255)
);

-- Table: COMMENT
CREATE TABLE COMMENT (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    CONTENT VARCHAR(2000),
    EVENT_ID BIGINT,
    POST_ID BIGINT,
    CREATED_AT TIMESTAMP,
    CREATED_BY VARCHAR(255)
);