alter table SPRINT_DAY drop constraint FKbt17br77tnhx5y8qgigcr8ffu;
alter table TASK_UPDATE drop constraint FKd4ssw8m2o5sdcxdqtilntpxhn;
drop table if exists SPRINT cascade;
drop table if exists SPRINT_DAY cascade;
drop table if exists TASK_UPDATE cascade;
drop sequence if exists SPRINT_DAY_ID_SEQ;
drop sequence if exists TASK_UPDATE_ID_SEQ;
