ALTER TABLE USERS ADD COLUMN  MEMBERSHIP_RENEW_DATE TIMESTAMP;
UPDATE USERS SET MEMBERSHIP_RENEW_DATE=NOW();
DROP TABLE SESSION_HISTORY;
CREATE TABLE SESSION_HISTORY (
       SESSION_HISTORY_ID BIGINT NOT NULL AUTO_INCREMENT
     , SESSION_ID VARCHAR(50)
     , USER_ID BIGINT NULL
     , BEGIN_DATE TIMESTAMP NOT NULL
     , END_DATE TIMESTAMP NULL
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
INSERT INTO CONFIGURATION (CONFIGURATION_ID,NAME,VALUE,DESCRIPTION,DATA_TYPE, CREATE_DATE,MOD_DATE,MOD_BY) VALUES
(1,'ANNUAL_FEE','20','Frais Annuel de Membre','String',NOW(),NOW(),1),
(2,'PAY_ONLINE','0','Payer a l''Enregistrement','Boolean',NOW(),NOW(),1),
(3,'ORG_EMAIL','ukadtogo@gmail.com','E-MAIL','String',NOW(),NOW(),1),
(4,'ORG_SMTP','smtp.gmail.com','SMTP','String',NOW(),NOW(),1),
(5,'ORG_WEBSITE','www.ukadtogo.com','Web site','String',NOW(),NOW(),1),
(6,'ORG_EMAIL_PASSWORD','ukadtogo123','E-MAIL PASSWORD ','String',NOW(),NOW(),1),
(7,'ORG_NAME','U.K.A.D e.V.','Nom de l''organisation','String',NOW(),NOW(),1),
(8,'ORG_ADDRESS','123 Rue des coteaux, Sherbrook CA.','Adresse de l''organisation','String',NOW(),NOW(),1) ; 

COMMIT;