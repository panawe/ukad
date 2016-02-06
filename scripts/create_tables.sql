DROP TABLE ROLES_USER;
DROP TABLE ROLES_MENU;
DROP TABLE SESSION_HISTORY;
DROP TABLE USERS;
DROP TABLE NEWS;
DROP TABLE MENU;
DROP TABLE ROLES;
DROP TABLE POSITION;
DROP TABLE MAIL;
DROP TABLE COUNTRY;
DROP TABLE EVENT;

CREATE TABLE ROLES (
       ROLE_ID BIGINT NOT NULL AUTO_INCREMENT
     , ROLE_CODE INTEGER
     , NAME VARCHAR(50) NOT NULL
     , DESCRIPTION VARCHAR(200)
     , CREATE_DATE TIMESTAMP
     , MOD_DATE TIMESTAMP
     , MOD_BY BIGINT
     , PRIMARY KEY (ROLE_ID)
);
CREATE UNIQUE INDEX UNIQUE_GROUPE ON ROLES ( NAME ASC);

CREATE TABLE COUNTRY (
       COUNTRY_ID BIGINT NOT NULL AUTO_INCREMENT
     , NAME VARCHAR(100) NOT NULL
     , DOMAIN CHAR(2)
     , CODE MEDIUMINT
     , POPULATION BIGINT
     , AREA DOUBLE
     , CREATE_DATE TIMESTAMP NOT NULL
     , MOD_DATE TIMESTAMP NOT NULL
     , MOD_BY BIGINT NOT NULL
     , PRIMARY KEY (COUNTRY_ID)
);

CREATE TABLE MENU (
       MENU_ID BIGINT NOT NULL AUTO_INCREMENT
     , PARENT_MENU_ID BIGINT
     , NAME VARCHAR(50) NOT NULL
     , SECURITY_CODE INTEGER
     , DESCRIPTION VARCHAR(200) NOT NULL
     , CREATE_DATE TIMESTAMP NOT NULL
     , MOD_DATE TIMESTAMP NOT NULL
     , MOD_BY BIGINT NOT NULL
     , URL VARCHAR(100)
     , PRIMARY KEY (MENU_ID)
);
CREATE UNIQUE INDEX UNIQUE_MENU ON MENU (NAME ASC, PARENT_MENU_ID ASC);

CREATE TABLE POSITION (
       POSITION_ID MEDIUMINT NOT NULL AUTO_INCREMENT
     , NAME VARCHAR(50) NOT NULL
     , CREATE_DATE TIMESTAMP NOT NULL
     , MOD_DATE TIMESTAMP NOT NULL
     , MOD_BY BIGINT NOT NULL
     , PRIMARY KEY (POSITION_ID)
);


CREATE TABLE NEWS (
       NEWS_ID BIGINT NOT NULL AUTO_INCREMENT
     , TITLE VARCHAR(100) NOT NULL
     , NEWS_DATE DATE NOT NULL
     , MESSAGE TEXT NOT NULL
     , PICTURE LONGBLOB
     , AUTHOR VARCHAR(100)
     , CREATE_DATE TIMESTAMP NOT NULL
     , MOD_DATE TIMESTAMP NOT NULL
     , MOD_BY BIGINT NOT NULL
     , PRIMARY KEY (NEWS_ID)
);

CREATE TABLE EVENT (
       EVENT_ID BIGINT NOT NULL AUTO_INCREMENT
     , TITLE VARCHAR(100) NOT NULL
     , BEGIN_TIME TIMESTAMP NOT NULL
     , END_TIME TIMESTAMP NOT NULL
     , DESCRIPTION TEXT  
     , ADDRESS TEXT
     , ALBUM_TAG VARCHAR(50)
     , ALBUM_NOTE VARCHAR(100)
     , CITY VARCHAR(150)
     , EVENT_LENGTH MEDIUMINT
     , CREATE_DATE TIMESTAMP NOT NULL
     , MOD_DATE TIMESTAMP NOT NULL
     , MOD_BY BIGINT NOT NULL
     , PRIMARY KEY (EVENT_ID)
);
ALTER TABLE EVENT MODIFY COLUMN BEGIN_TIME TIMESTAMP NOT NULL
      COMMENT 'FORMAT HH24:MI (14:50)';
ALTER TABLE EVENT MODIFY COLUMN END_TIME TIMESTAMP NOT NULL
      COMMENT 'FORMAT HH24:MI (14:50)';

CREATE TABLE USERS (
       USER_ID BIGINT NOT NULL AUTO_INCREMENT
     , USER_NAME VARCHAR(50) NOT NULL
     , PASSWORD VARCHAR(50) NOT NULL
     , FIRST_NAME VARCHAR(50) NOT NULL
     , LAST_NAME VARCHAR(50) NOT NULL
     , CAN_APPROVE TINYINT NOT NULL DEFAULT 0
     , E_MAIL VARCHAR(50)
     , pageSkin VARCHAR(15) 
     , CURRENT_LOCALE VARCHAR(10)
     , CSV_DELIMITER VARCHAR(10)
     , CREATE_DATE TIMESTAMP NOT NULL DEFAULT NOW()
     , MOD_DATE TIMESTAMP NOT NULL  
     , MOD_BY BIGINT NOT NULL DEFAULT 1
     , PHONE VARCHAR(20)
     , ADDRESS TEXT
     , CITY VARCHAR(150)
     , PIC VARCHAR(100)
     , COUNTRY_ID BIGINT NOT NULL DEFAULT 5
     , ZIP_CODE  VARCHAR(10)
     , POSITION_ID MEDIUMINT DEFAULT 1
     , STATUS SMALLINT DEFAULT 0
     , PRIMARY KEY (USER_ID)
     , INDEX (POSITION_ID)
     , CONSTRAINT FK_USERS_1 FOREIGN KEY (POSITION_ID)
                  REFERENCES POSITION (POSITION_ID)
);
ALTER TABLE USERS MODIFY COLUMN POSITION_ID MEDIUMINT
      COMMENT 'DECRIT LE POST OCCUPE';
ALTER TABLE USERS MODIFY COLUMN STATUS SMALLINT DEFAULT 1
      COMMENT '0-inactive, 1- active';
CREATE UNIQUE INDEX UNIQUE_USER ON USERS (USER_NAME ASC);

CREATE TABLE SESSION_HISTORY (
       SESSION_HISTORY_ID BIGINT NOT NULL AUTO_INCREMENT
     , SESSION_ID VARCHAR(50)
     , USER_ID BIGINT NOT NULL
     , BEGIN_DATE TIMESTAMP NOT NULL
     , END_DATE TIMESTAMP NOT NULL
     , CREATE_DATE TIMESTAMP NOT NULL
     , MOD_DATE TIMESTAMP NOT NULL
     , MOD_BY BIGINT NOT NULL
     , HOST_NAME VARCHAR(50)
     , HOST_IP VARCHAR(50)
     , BROWSER VARCHAR(250)
     , LANGUAGE VARCHAR(50)
     , OSUSER VARCHAR(50)
     , PRIMARY KEY (SESSION_HISTORY_ID)
     , INDEX (USER_ID)
     , CONSTRAINT FK_SESSION_HISTORY_1 FOREIGN KEY (USER_ID)
                  REFERENCES USERS (USER_ID)
);


CREATE TABLE ROLES_USER (
       ROLE_USER_ID BIGINT NOT NULL AUTO_INCREMENT
     , SCHOOL_ID MEDIUMINT NOT NULL
     , ROLE_ID BIGINT NOT NULL
     , USER_ID BIGINT NOT NULL
     , CREATE_DATE TIMESTAMP NOT NULL
     , MOD_DATE TIMESTAMP NOT NULL
     , MOD_BY BIGINT NOT NULL
     , PRIMARY KEY (ROLE_USER_ID)
     , INDEX (USER_ID)
     , CONSTRAINT FK_ROLES_USER_1 FOREIGN KEY (USER_ID)
                  REFERENCES USERS (USER_ID)
     , INDEX (ROLE_ID)
     , CONSTRAINT FK_ROLES_USER_2 FOREIGN KEY (ROLE_ID)
                  REFERENCES ROLES (ROLE_ID)
);

CREATE TABLE ROLES_MENU (
       ROLE_MENU_ID BIGINT NOT NULL AUTO_INCREMENT
     , SCHOOL_ID MEDIUMINT NOT NULL
     , ROLE_ID BIGINT NOT NULL
     , MENU_ID BIGINT NOT NULL
     , CREATE_DATE TIMESTAMP NOT NULL
     , MOD_DATE TIMESTAMP NOT NULL
     , MOD_BY BIGINT NOT NULL
     , ACCESS_LEVEL TINYINT NOT NULL
     , PRIMARY KEY (ROLE_MENU_ID)
     , INDEX (ROLE_ID)
     , CONSTRAINT FK_ROLES_MENU_1 FOREIGN KEY (ROLE_ID)
                  REFERENCES ROLES (ROLE_ID)
     , INDEX (MENU_ID)
     , CONSTRAINT FK_ROLES_MENU_2 FOREIGN KEY (MENU_ID)
                  REFERENCES MENU (MENU_ID)
);

CREATE TABLE MAIL (
       MAIL_ID BIGINT NOT NULL AUTO_INCREMENT
     , SUBJECT TEXT NOT NULL
     , BODY TEXT NOT NULL
     , STATUS SMALLINT DEFAULT 0
     , USER_ID BIGINT
     , CREATE_DATE TIMESTAMP NOT NULL
     , MOD_DATE TIMESTAMP NOT NULL
     , MOD_BY BIGINT NOT NULL
     , PRIMARY KEY (MAIL_ID)
);
