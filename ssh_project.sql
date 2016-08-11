----------------------------------------------------
-- Export file for user SSH                       --
-- Created by adminitation on 2015/9/15, 23:31:58 --
----------------------------------------------------

set define off
spool ssh_project.log

prompt
prompt Creating table SYS_AREA
prompt =======================
prompt
create table SSH.SYS_AREA
(
  area_id   NUMBER not null,
  area_name VARCHAR2(100) not null,
  city_id   NUMBER not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SSH.SYS_AREA
  add constraint PK_AREAID primary key (AREA_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_CITY
prompt =======================
prompt
create table SSH.SYS_CITY
(
  city_id     NUMBER not null,
  city_name   VARCHAR2(100) not null,
  province_id NUMBER not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SSH.SYS_CITY
  add constraint PK_CITYID primary key (CITY_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_MENU
prompt =======================
prompt
create table SSH.SYS_MENU
(
  menu_id     NUMBER not null,
  menu_name   VARCHAR2(50) not null,
  menu_href   VARCHAR2(50),
  menu_target VARCHAR2(50),
  parentid    NUMBER(10) not null,
  grade       NUMBER not null,
  isleaf      NUMBER not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SSH.SYS_MENU
  add constraint PK_MENUID primary key (MENU_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SSH.SYS_MENU
  add constraint FK_MENUNAME unique (MENU_NAME)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_NOTICE
prompt =========================
prompt
create table SSH.SYS_NOTICE
(
  notice_id      NUMBER not null,
  notice_title   VARCHAR2(100),
  notice_adduser VARCHAR2(20),
  notice_addtime VARCHAR2(20),
  notice_content CLOB,
  filename       VARCHAR2(50),
  filedata       BLOB,
  is_approve     NUMBER(1),
  approve_user   VARCHAR2(20),
  approve_time   VARCHAR2(20)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SSH.SYS_NOTICE
  add constraint PK_NOTICE_ID primary key (NOTICE_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_PROVINCE
prompt ===========================
prompt
create table SSH.SYS_PROVINCE
(
  province_id   NUMBER not null,
  province_name VARCHAR2(100) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SSH.SYS_PROVINCE
  add constraint PK_PROVINCEID primary key (PROVINCE_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_ROLE
prompt =======================
prompt
create table SSH.SYS_ROLE
(
  role_id     NUMBER(4) not null,
  role_name   VARCHAR2(40) not null,
  role_remark VARCHAR2(200)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SSH.SYS_ROLE
  add constraint PK_ROLEID primary key (ROLE_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_ROLE_MENU
prompt ============================
prompt
create table SSH.SYS_ROLE_MENU
(
  role_id NUMBER(10) not null,
  menu_id NUMBER(10) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SSH.SYS_ROLE_MENU
  add constraint PK_ROLE_MENU primary key (ROLE_ID, MENU_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_USERTIP
prompt ==========================
prompt
create table SSH.SYS_USERTIP
(
  userid        NUMBER not null,
  is_view_tip   NUMBER(1),
  interval_time VARCHAR2(20),
  view_tip_attr VARCHAR2(20)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SSH.SYS_USERTIP
  add constraint PK_TIP_ID primary key (USERID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_USER_PARAM
prompt =============================
prompt
create table SSH.SYS_USER_PARAM
(
  userid      VARCHAR2(10) not null,
  param_key   VARCHAR2(100) not null,
  param_value VARCHAR2(100)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SSH.SYS_USER_PARAM
  add constraint PK_USER_PARAM primary key (USERID, PARAM_KEY)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_USER_ROLE
prompt ============================
prompt
create table SSH.SYS_USER_ROLE
(
  user_id VARCHAR2(20) not null,
  role_id VARCHAR2(20) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SSH.SYS_USER_ROLE
  add constraint PK_USER_ROLE primary key (USER_ID, ROLE_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table USERINFO
prompt =======================
prompt
create table SSH.USERINFO
(
  userid   NUMBER not null,
  username VARCHAR2(100),
  password VARCHAR2(100),
  truename VARCHAR2(100),
  usersex  VARCHAR2(100),
  userage  NUMBER,
  deptid   NUMBER,
  salary   NUMBER,
  telphone NUMBER,
  address  VARCHAR2(100),
  deptname VARCHAR2(100),
  birthday VARCHAR2(100),
  mail     VARCHAR2(100),
  is_admin NUMBER
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SSH.USERINFO
  add constraint PK_USERID primary key (USERID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating sequence SEQ_NOTICE
prompt ============================
prompt
create sequence SSH.SEQ_NOTICE
minvalue 1
maxvalue 99999999999999999999
start with 81
increment by 1
cache 20;


spool off
