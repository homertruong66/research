alter table ITEM drop foreign key FK227313CE1C4DD5;
alter table MAILER drop foreign key FK871B74A4AFC3BBB5;
alter table ORGANIZATION drop foreign key ORGANIZATION_ID;
alter table ORGANIZATION drop foreign key FKD063D533CAEA7795;
alter table ORGANIZATION drop foreign key FKD063D533EFDBC6BF;
alter table PERSON drop foreign key FK8C768F5537E7B61F;
alter table PERSON drop foreign key FK8C768F55845C7435;
alter table PERSON drop foreign key PERSON_ID;
alter table PERSON drop foreign key FK8C768F55527BF0AD;
alter table PERSON drop foreign key FK8C768F55BC4F8B67;
alter table PROVINCE drop foreign key FKF3CA1B30EFDBC6BF;
alter table RECIPIENT drop foreign key FKD6F981F9CE1C4DD5;
alter table USER drop foreign key FK27E3CB9059B1FF;
alter table USER_ROLE drop foreign key FKBC16F46AD0504F55;
alter table USER_ROLE drop foreign key FKBC16F46A757B1335;
alter table VOTE drop foreign key FK284AEA5978257F;
alter table VOTE drop foreign key FK284AEA757B1335;
alter table VOTE_PLACE drop foreign key FKD0623752B847F25F;
alter table VOTE_PLACE drop foreign key FKD0623752A45BCAD5;
drop table if exists COUNTRY;
drop table if exists EVENT;
drop table if exists ITEM;
drop table if exists MAIL;
drop table if exists MAILER;
drop table if exists ORGANIZATION;
drop table if exists PARTY;
drop table if exists PERSON;
drop table if exists PLACE;
drop table if exists PROVINCE;
drop table if exists RECIPIENT;
drop table if exists ROLE;
drop table if exists USER;
drop table if exists USER_ROLE;
drop table if exists VOTE;
drop table if exists VOTE_PLACE;
create table COUNTRY (ID BIGINT not null, VERSION integer not null, CODE VARCHAR(6), NAME VARCHAR(256), primary key (ID)) type=InnoDB;
create table EVENT (ID BIGINT not null, VERSION integer not null, NAME VARCHAR(256) not null, DATE VARCHAR(256) not null, PLACE VARCHAR(256) not null, RESERVER VARCHAR(256) not null, primary key (ID)) type=InnoDB;
create table ITEM (ID BIGINT not null, VERSION integer not null, NAME VARCHAR(256) not null, DESCRIPTION VARCHAR(512) not null, DATE_CREATED VARCHAR(256) not null, DATE_MODIFIED VARCHAR(256) not null, MAILER_ID bigint not null, primary key (ID)) type=InnoDB;
create table MAIL (ID BIGINT not null, VERSION integer not null, HOST VARCHAR(256) not null, USERNAME VARCHAR(256) not null, PASSWORD VARCHAR(256) not null, SENDER_HOST VARCHAR(256) not null, SENDER_ADDRESS VARCHAR(256) not null, SENDER_NAME VARCHAR(256), SUBJECT VARCHAR(256) not null, TIME_INTERVAL INTEGER not null, primary key (ID)) type=InnoDB;
create table MAILER (ID BIGINT not null, VERSION integer not null, NAME VARCHAR(256) not null, MAIL_ID bigint, primary key (ID)) type=InnoDB;
create table ORGANIZATION (ID BIGINT not null, NAME VARCHAR(256) not null, STREET VARCHAR(256), CITY VARCHAR(256), PHONE VARCHAR(256), POSTAL_OR_ZIP_CODE VARCHAR(6), PROVINCE_ID bigint, COUNTRY_ID bigint, primary key (ID)) type=InnoDB;
create table PARTY (ID BIGINT not null, VERSION integer not null, EMAIL VARCHAR(256), primary key (ID)) type=InnoDB;
create table PERSON (ID BIGINT not null, FIRST_NAME VARCHAR(256) not null, LAST_NAME VARCHAR(256) not null, FULL_NAME VARCHAR(256) not null, SEX varchar(255), ETHNIC_GROUP varchar(255), HOME_STREET VARCHAR(256), HOME_CITY VARCHAR(256), HOME_PHONE VARCHAR(256), HOME_POSTAL_OR_ZIP_CODE VARCHAR(6), HOME_PROVINCE_ID bigint, HOME_COUNTRY_ID bigint, WORK_STREET VARCHAR(256), WORK_CITY VARCHAR(256), WORK_PHONE VARCHAR(256), WORK_POSTAL_OR_ZIP_CODE VARCHAR(6), WORK_PROVINCE_ID bigint, WORK_COUNTRY_ID bigint, primary key (ID)) type=InnoDB;
create table PLACE (ID BIGINT not null, VERSION integer not null, NAME VARCHAR(256) not null, ACTIVITIES VARCHAR(256) not null, ADDRESS VARCHAR(256) not null, PHONE VARCHAR(256) not null, primary key (ID)) type=InnoDB;
create table PROVINCE (ID BIGINT not null, CODE VARCHAR(6), NAME VARCHAR(256), COUNTRY_ID bigint not null, primary key (ID)) type=InnoDB;
create table RECIPIENT (ID BIGINT not null, VERSION integer not null, NAME VARCHAR(256) not null, ADDRESS VARCHAR(256) not null, MAILER_ID bigint, primary key (ID)) type=InnoDB;
create table ROLE (ID BIGINT not null, VERSION integer not null, NAME VARCHAR(256), primary key (ID)) type=InnoDB;
create table USER (ID BIGINT not null, ENTITY_DISCRIMINATOR varchar(255) not null, VERSION integer not null, USER_NAME VARCHAR(128) not null unique, PASSWORD VARCHAR(256) not null, ENABLED BIT, IS_BELONG_ORGANIZATION BIT default 0, FULL_NAME VARCHAR(128), EMAIL VARCHAR(256), PARTY_ID bigint, primary key (ID)) type=InnoDB;
create table USER_ROLE (ID BIGINT not null, EXPIRY_DATE DATETIME not null, ROLE_ID bigint, USER_ID bigint, primary key (ID)) type=InnoDB;
create table VOTE (ID BIGINT not null, VERSION integer not null, DATE VARCHAR(256) not null, EVENT_ID bigint, USER_ID bigint, primary key (ID)) type=InnoDB;
create table VOTE_PLACE (VOTE_ID bigint not null, PLACE_ID bigint not null, primary key (VOTE_ID, PLACE_ID)) type=InnoDB;
alter table ITEM add index FK227313CE1C4DD5 (MAILER_ID), add constraint FK227313CE1C4DD5 foreign key (MAILER_ID) references MAILER (ID);
alter table MAILER add index FK871B74A4AFC3BBB5 (MAIL_ID), add constraint FK871B74A4AFC3BBB5 foreign key (MAIL_ID) references MAIL (ID);
create index IX_PROVINCE on ORGANIZATION (PROVINCE_ID);
create index IX_COUNTRY on ORGANIZATION (COUNTRY_ID);
alter table ORGANIZATION add index ORGANIZATION_ID (ID), add constraint ORGANIZATION_ID foreign key (ID) references PARTY (ID);
alter table ORGANIZATION add index FKD063D533CAEA7795 (PROVINCE_ID), add constraint FKD063D533CAEA7795 foreign key (PROVINCE_ID) references PROVINCE (ID);
alter table ORGANIZATION add index FKD063D533EFDBC6BF (COUNTRY_ID), add constraint FKD063D533EFDBC6BF foreign key (COUNTRY_ID) references COUNTRY (ID);
create index IX_HOME_COUNTRY on PERSON (HOME_COUNTRY_ID);
create index IX_HOME_PROVINCE on PERSON (HOME_PROVINCE_ID);
create index IX_WORK_COUNTRY on PERSON (WORK_COUNTRY_ID);
create index IX_WORK_PROVINCE on PERSON (WORK_PROVINCE_ID);
alter table PERSON add index FK8C768F5537E7B61F (HOME_COUNTRY_ID), add constraint FK8C768F5537E7B61F foreign key (HOME_COUNTRY_ID) references COUNTRY (ID);
alter table PERSON add index FK8C768F55845C7435 (HOME_PROVINCE_ID), add constraint FK8C768F55845C7435 foreign key (HOME_PROVINCE_ID) references PROVINCE (ID);
alter table PERSON add index PERSON_ID (ID), add constraint PERSON_ID foreign key (ID) references PARTY (ID);
alter table PERSON add index FK8C768F55527BF0AD (WORK_COUNTRY_ID), add constraint FK8C768F55527BF0AD foreign key (WORK_COUNTRY_ID) references COUNTRY (ID);
alter table PERSON add index FK8C768F55BC4F8B67 (WORK_PROVINCE_ID), add constraint FK8C768F55BC4F8B67 foreign key (WORK_PROVINCE_ID) references PROVINCE (ID);
alter table PROVINCE add index FKF3CA1B30EFDBC6BF (COUNTRY_ID), add constraint FKF3CA1B30EFDBC6BF foreign key (COUNTRY_ID) references COUNTRY (ID);
alter table RECIPIENT add index FKD6F981F9CE1C4DD5 (MAILER_ID), add constraint FKD6F981F9CE1C4DD5 foreign key (MAILER_ID) references MAILER (ID);
create index IX_PARTY on USER (PARTY_ID);
alter table USER add index FK27E3CB9059B1FF (PARTY_ID), add constraint FK27E3CB9059B1FF foreign key (PARTY_ID) references PARTY (ID);
create index IX_ROLE on USER_ROLE (ROLE_ID);
alter table USER_ROLE add index FKBC16F46AD0504F55 (ROLE_ID), add constraint FKBC16F46AD0504F55 foreign key (ROLE_ID) references ROLE (ID);
alter table USER_ROLE add index FKBC16F46A757B1335 (USER_ID), add constraint FKBC16F46A757B1335 foreign key (USER_ID) references USER (ID);
alter table VOTE add index FK284AEA5978257F (EVENT_ID), add constraint FK284AEA5978257F foreign key (EVENT_ID) references EVENT (ID);
alter table VOTE add index FK284AEA757B1335 (USER_ID), add constraint FK284AEA757B1335 foreign key (USER_ID) references USER (ID);
alter table VOTE_PLACE add index FKD0623752B847F25F (PLACE_ID), add constraint FKD0623752B847F25F foreign key (PLACE_ID) references PLACE (ID);
alter table VOTE_PLACE add index FKD0623752A45BCAD5 (VOTE_ID), add constraint FKD0623752A45BCAD5 foreign key (VOTE_ID) references VOTE (ID);
