create sequence SPRINT_DAY_ID_SEQ start 1 increment 1;
create sequence TASK_UPDATE_ID_SEQ start 1 increment 1;
create table SPRINT (SPRINT_ID int4 not null, INITIAL_TOTAL_HOURS int4 not null, NO_DAYS int4 not null, SPRINT_NUMBER int4 not null, START_DATE varchar(255) not null, primary key (SPRINT_ID));
create table SPRINT_DAY (SPRINT_DAY_ID int4 not null, DAY_NO int4 not null, HOURS_TOTAL int4, SPRINT_DAY varchar(255) not null, SPRINT_ID int4 not null, primary key (SPRINT_DAY_ID));
create table TASK (TASK_ID int4 not null, INITIAL_HOURS int4 not null, REMAINING_HOURS int4 not null, SPRINT_ID int4 not null, primary key (TASK_ID));
create table TASK_UPDATE (TASK_UPDATE_ID int4 not null, REMAINING_HOURS int4 not null, TASK_ID int4 not null, SPRINT_DAY_ID int4 not null, primary key (TASK_UPDATE_ID));
alter table SPRINT_DAY add constraint FKbt17br77tnhx5y8qgigcr8ffu foreign key (SPRINT_ID) references SPRINT;
alter table TASK add constraint FKjnwgruq3wykrihc20q1so5vcg foreign key (SPRINT_ID) references SPRINT;
alter table TASK_UPDATE add constraint FKd4ssw8m2o5sdcxdqtilntpxhn foreign key (SPRINT_DAY_ID) references SPRINT_DAY;
