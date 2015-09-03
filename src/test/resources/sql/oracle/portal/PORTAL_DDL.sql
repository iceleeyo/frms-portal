-- ----------------------------
-- Table structure for COUNTRIES_CODE
-- ----------------------------
CREATE TABLE "COUNTRIES_CODE" (
"ID" NUMBER(10) NOT NULL ,
"LETTER_CODE" VARCHAR2(3 CHAR) NULL ,
"DIGITAL_CODE" VARCHAR2(3 CHAR) NULL ,
"FOR_SHORT" VARCHAR2(55 CHAR) NULL ,
"FULL_NAME" VARCHAR2(255 CHAR) NULL ,
"CREATE_TIME" TIMESTAMP(6)  NULL ,
"UPDATE_TIME" TIMESTAMP(6)  NULL ,
"MODIFER" VARCHAR2(20 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
COMMENT ON COLUMN "COUNTRIES_CODE"."ID" IS '主键';
COMMENT ON COLUMN "COUNTRIES_CODE"."LETTER_CODE" IS '字母编码';
COMMENT ON COLUMN "COUNTRIES_CODE"."DIGITAL_CODE" IS '数字编码';
COMMENT ON COLUMN "COUNTRIES_CODE"."FOR_SHORT" IS '国家简称';
COMMENT ON COLUMN "COUNTRIES_CODE"."FULL_NAME" IS '国家全称';
COMMENT ON COLUMN "COUNTRIES_CODE"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "COUNTRIES_CODE"."UPDATE_TIME" IS '更新时间';
COMMENT ON COLUMN "COUNTRIES_CODE"."MODIFER" IS '更新者';
-- ----------------------------
-- Indexes structure for table COUNTRIES_CODE
-- ----------------------------

-- ----------------------------
-- Checks structure for table COUNTRIES_CODE
-- ----------------------------
ALTER TABLE "COUNTRIES_CODE" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table COUNTRIES_CODE
-- ----------------------------
ALTER TABLE "COUNTRIES_CODE" ADD PRIMARY KEY ("ID");


-- ----------------------------
-- Table structure for CURRENCY_CODE
-- ----------------------------
CREATE TABLE "CURRENCY_CODE" (
"ID" NUMBER(10) NOT NULL ,
"LETTER_CODE" VARCHAR2(3 CHAR) NULL ,
"DIGITAL_CODE" VARCHAR2(3 CHAR) NULL ,
"NAME" VARCHAR2(20 CHAR) NULL ,
"CREATE_TIME" TIMESTAMP(6)  NULL ,
"UPDATE_TIME" TIMESTAMP(6)  NULL ,
"MODIFER" VARCHAR2(20 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
COMMENT ON COLUMN "CURRENCY_CODE"."ID" IS '主键';
COMMENT ON COLUMN "CURRENCY_CODE"."LETTER_CODE" IS '字母编码';
COMMENT ON COLUMN "CURRENCY_CODE"."DIGITAL_CODE" IS '数字编码';
COMMENT ON COLUMN "CURRENCY_CODE"."NAME" IS '货币名称';
COMMENT ON COLUMN "CURRENCY_CODE"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "CURRENCY_CODE"."UPDATE_TIME" IS '更新时间';
COMMENT ON COLUMN "CURRENCY_CODE"."MODIFER" IS '更新者';
-- ----------------------------
-- Indexes structure for table CURRENCY_CODE
-- ----------------------------

-- ----------------------------
-- Checks structure for table CURRENCY_CODE
-- ----------------------------
ALTER TABLE "CURRENCY_CODE" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table CURRENCY_CODE
-- ----------------------------
ALTER TABLE "CURRENCY_CODE" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Table structure for DIMENSION
-- ----------------------------
CREATE TABLE "DIMENSION" (
"ID" NUMBER(8) NOT NULL ,
"NAME" VARCHAR2(40 BYTE) NOT NULL ,
"READONLY" NUMBER(1) NULL ,
"MEMO" VARCHAR2(200 BYTE) NULL ,
"TYPE" NUMBER(6) NOT NULL ,
"ENABLED" NUMBER(1) NULL ,
"CREATE_TIME" TIMESTAMP(6)  NULL ,
"UPDATE_TIME" TIMESTAMP(6)  NULL ,
"MODIFER" VARCHAR2(20 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
COMMENT ON COLUMN "DIMENSION"."ID" IS '维度ID,主键自动递增';
COMMENT ON COLUMN "DIMENSION"."NAME" IS '名称';
COMMENT ON COLUMN "DIMENSION"."READONLY" IS '是否只读:1只读';
COMMENT ON COLUMN "DIMENSION"."MEMO" IS '备注';
COMMENT ON COLUMN "DIMENSION"."TYPE" IS '是否是递归因子:0:否,1是';
COMMENT ON COLUMN "DIMENSION"."ENABLED" IS '状态:0删除,1正常,2禁用';
COMMENT ON COLUMN "DIMENSION"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "DIMENSION"."UPDATE_TIME" IS '最后更新时间';
COMMENT ON COLUMN "DIMENSION"."MODIFER" IS '更新者';
-- ----------------------------
-- Checks structure for table DIMENSION
-- ----------------------------
ALTER TABLE "DIMENSION" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "DIMENSION" ADD CHECK ("NAME" IS NOT NULL);
ALTER TABLE "DIMENSION" ADD CHECK ("TYPE" IS NOT NULL);
-- ----------------------------
-- Primary Key structure for table DIMENSION
-- ----------------------------
ALTER TABLE "DIMENSION" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Table structure for LOGGING_EVENT
-- ----------------------------
CREATE TABLE "LOGGING_EVENT" (
"TIMESTMP" TIMESTAMP(3)  NOT NULL ,
"FORMATTED_MESSAGE" VARCHAR2(4000 BYTE) NOT NULL ,
"LOGGER_NAME" VARCHAR2(254 BYTE) NOT NULL ,
"LEVEL_STRING" VARCHAR2(254 BYTE) NOT NULL ,
"THREAD_NAME" VARCHAR2(254 BYTE) NULL ,
"REFERENCE_FLAG" NUMBER NULL ,
"ARG0" VARCHAR2(254 BYTE) NULL ,
"ARG1" VARCHAR2(254 BYTE) NULL ,
"ARG2" VARCHAR2(254 BYTE) NULL ,
"ARG3" VARCHAR2(254 BYTE) NULL ,
"CALLER_FILENAME" VARCHAR2(254 BYTE) NOT NULL ,
"CALLER_CLASS" VARCHAR2(254 BYTE) NOT NULL ,
"CALLER_METHOD" VARCHAR2(254 BYTE) NOT NULL ,
"CALLER_LINE" CHAR(4 BYTE) NOT NULL ,
"EVENT_ID" NUMBER(10) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
-- ----------------------------
-- Checks structure for table LOGGING_EVENT
-- ----------------------------
ALTER TABLE "LOGGING_EVENT" ADD CHECK ("TIMESTMP" IS NOT NULL);
ALTER TABLE "LOGGING_EVENT" ADD CHECK ("FORMATTED_MESSAGE" IS NOT NULL);
ALTER TABLE "LOGGING_EVENT" ADD CHECK ("LOGGER_NAME" IS NOT NULL);
ALTER TABLE "LOGGING_EVENT" ADD CHECK ("LEVEL_STRING" IS NOT NULL);
ALTER TABLE "LOGGING_EVENT" ADD CHECK ("CALLER_FILENAME" IS NOT NULL);
ALTER TABLE "LOGGING_EVENT" ADD CHECK ("CALLER_CLASS" IS NOT NULL);
ALTER TABLE "LOGGING_EVENT" ADD CHECK ("CALLER_METHOD" IS NOT NULL);
ALTER TABLE "LOGGING_EVENT" ADD CHECK ("CALLER_LINE" IS NOT NULL);
-- ----------------------------
-- Primary Key structure for table LOGGING_EVENT
-- ----------------------------
ALTER TABLE "LOGGING_EVENT" ADD PRIMARY KEY ("EVENT_ID");

-- ----------------------------
-- Table structure for LOGGING_EVENT_EXCEPTION
-- ----------------------------
CREATE TABLE "LOGGING_EVENT_EXCEPTION" (
"EVENT_ID" NUMBER(10) NOT NULL ,
"I" NUMBER NOT NULL ,
"TRACE_LINE" VARCHAR2(254 BYTE) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
-- ----------------------------
-- Checks structure for table LOGGING_EVENT_EXCEPTION
-- ----------------------------
ALTER TABLE "LOGGING_EVENT_EXCEPTION" ADD CHECK ("EVENT_ID" IS NOT NULL);
ALTER TABLE "LOGGING_EVENT_EXCEPTION" ADD CHECK ("I" IS NOT NULL);
ALTER TABLE "LOGGING_EVENT_EXCEPTION" ADD CHECK ("TRACE_LINE" IS NOT NULL);
-- ----------------------------
-- Primary Key structure for table LOGGING_EVENT_EXCEPTION
-- ----------------------------
ALTER TABLE "LOGGING_EVENT_EXCEPTION" ADD PRIMARY KEY ("EVENT_ID", "I");

-- ----------------------------
-- Table structure for LOGGING_EVENT_PROPERTY
-- ----------------------------
CREATE TABLE "LOGGING_EVENT_PROPERTY" (
"EVENT_ID" NUMBER(10) NOT NULL ,
"MAPPED_KEY" VARCHAR2(254 BYTE) NOT NULL ,
"MAPPED_VALUE" VARCHAR2(1024 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
-- ----------------------------
-- Checks structure for table LOGGING_EVENT_PROPERTY
-- ----------------------------
ALTER TABLE "LOGGING_EVENT_PROPERTY" ADD CHECK ("EVENT_ID" IS NOT NULL);
ALTER TABLE "LOGGING_EVENT_PROPERTY" ADD CHECK ("MAPPED_KEY" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table LOGGING_EVENT_PROPERTY
-- ----------------------------
ALTER TABLE "LOGGING_EVENT_PROPERTY" ADD PRIMARY KEY ("EVENT_ID", "MAPPED_KEY");

-- ----------------------------
-- Foreign Key structure for table "LOGGING_EVENT_EXCEPTION"
-- ----------------------------
ALTER TABLE "LOGGING_EVENT_EXCEPTION" ADD FOREIGN KEY ("EVENT_ID") REFERENCES "LOGGING_EVENT" ("EVENT_ID");

-- ----------------------------
-- Foreign Key structure for table "LOGGING_EVENT_PROPERTY"
-- ----------------------------
ALTER TABLE "LOGGING_EVENT_PROPERTY" ADD FOREIGN KEY ("EVENT_ID") REFERENCES "LOGGING_EVENT" ("EVENT_ID");


-- ----------------------------
-- Table structure for REGIERUNGSBEZIRK_CODE
-- ----------------------------
CREATE TABLE "REGIERUNGSBEZIRK_CODE" (
"ID" NUMBER(10) NOT NULL ,
"CODE" VARCHAR2(10 CHAR) NULL ,
"NAME" VARCHAR2(50 CHAR) NULL ,
"CREATE_TIME" TIMESTAMP(6)  NULL ,
"UPDATE_TIME" TIMESTAMP(6)  NULL ,
"MODIFER" VARCHAR2(20 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
COMMENT ON COLUMN "REGIERUNGSBEZIRK_CODE"."ID" IS '主键';
COMMENT ON COLUMN "REGIERUNGSBEZIRK_CODE"."CODE" IS '行政区编码';
COMMENT ON COLUMN "REGIERUNGSBEZIRK_CODE"."NAME" IS '行政区名';
COMMENT ON COLUMN "REGIERUNGSBEZIRK_CODE"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "REGIERUNGSBEZIRK_CODE"."UPDATE_TIME" IS '更新时间';
COMMENT ON COLUMN "REGIERUNGSBEZIRK_CODE"."MODIFER" IS '更新者';
-- ----------------------------
-- Indexes structure for table REGIERUNGSBEZIRK_CODE
-- ----------------------------

-- ----------------------------
-- Checks structure for table REGIERUNGSBEZIRK_CODE
-- ----------------------------
ALTER TABLE "REGIERUNGSBEZIRK_CODE" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table REGIERUNGSBEZIRK_CODE
-- ----------------------------
ALTER TABLE "REGIERUNGSBEZIRK_CODE" ADD PRIMARY KEY ("ID");


-- ----------------------------
-- Table structure for RESOURCES
-- ----------------------------
CREATE TABLE "RESOURCES" (
"ID" NUMBER(8) NOT NULL ,
"PARENT_ID" NUMBER(8) NOT NULL ,
"ENABLED" NUMBER(1) DEFAULT 1  NOT NULL ,
"RESOURCE_NAME" VARCHAR2(50 BYTE) NOT NULL ,
"RESOURCE_LEVEL" NUMBER(8) NOT NULL ,
"RESOURCE_TYPE" NUMBER(8) NOT NULL ,
"RESOURCE_DESC" VARCHAR2(50 BYTE) NULL ,
"RESOURCE_CODE" VARCHAR2(20 BYTE) NULL ,
"RESOURCE_URL" VARCHAR2(100 BYTE) NULL ,
"RESOURCE_ICON" VARCHAR2(255 BYTE) NULL ,
"CREATE_TIME" TIMESTAMP(6)  NULL ,
"UPDATE_TIME" TIMESTAMP(6)  NULL ,
"MODIFER" VARCHAR2(20 BYTE) NULL ,
"ORDER_FIELD" NUMBER(10) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
COMMENT ON COLUMN "RESOURCES"."ID" IS '资源ID,主键,自动递增';
COMMENT ON COLUMN "RESOURCES"."PARENT_ID" IS '父级资源ID';
COMMENT ON COLUMN "RESOURCES"."ENABLED" IS '状态:0删除,1正常,2禁用';
COMMENT ON COLUMN "RESOURCES"."RESOURCE_NAME" IS '资源名称';
COMMENT ON COLUMN "RESOURCES"."RESOURCE_LEVEL" IS '资源级别';
COMMENT ON COLUMN "RESOURCES"."RESOURCE_TYPE" IS '资源类型  1 菜单 0 非菜单';
COMMENT ON COLUMN "RESOURCES"."RESOURCE_DESC" IS '资源描述';
COMMENT ON COLUMN "RESOURCES"."RESOURCE_CODE" IS '方法上的code,用来控制权限';
COMMENT ON COLUMN "RESOURCES"."RESOURCE_URL" IS '资源链接地址';
COMMENT ON COLUMN "RESOURCES"."RESOURCE_ICON" IS '资源图片';
COMMENT ON COLUMN "RESOURCES"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "RESOURCES"."UPDATE_TIME" IS '更新时间';
COMMENT ON COLUMN "RESOURCES"."MODIFER" IS '更新者';
COMMENT ON COLUMN "RESOURCES"."ORDER_FIELD" IS '排序字段';
-- ----------------------------
-- Checks structure for table RESOURCES
-- ----------------------------
ALTER TABLE "RESOURCES" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "RESOURCES" ADD CHECK ("PARENT_ID" IS NOT NULL);
ALTER TABLE "RESOURCES" ADD CHECK ("ENABLED" IS NOT NULL);
ALTER TABLE "RESOURCES" ADD CHECK ("RESOURCE_NAME" IS NOT NULL);
ALTER TABLE "RESOURCES" ADD CHECK ("RESOURCE_LEVEL" IS NOT NULL);
ALTER TABLE "RESOURCES" ADD CHECK ("RESOURCE_TYPE" IS NOT NULL);
ALTER TABLE "RESOURCES" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "RESOURCES" ADD CHECK ("PARENT_ID" IS NOT NULL);
ALTER TABLE "RESOURCES" ADD CHECK ("ENABLED" IS NOT NULL);
ALTER TABLE "RESOURCES" ADD CHECK ("RESOURCE_NAME" IS NOT NULL);
ALTER TABLE "RESOURCES" ADD CHECK ("RESOURCE_LEVEL" IS NOT NULL);
ALTER TABLE "RESOURCES" ADD CHECK ("RESOURCE_TYPE" IS NOT NULL);
-- ----------------------------
-- Primary Key structure for table RESOURCES
-- ----------------------------
ALTER TABLE "RESOURCES" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Table structure for RISK_LEVEL
-- ----------------------------
CREATE TABLE "RISK_LEVEL" (
"ID" NUMBER(8) NOT NULL ,
"LEVEL_VAL" NUMBER(18) NULL ,
"NAME" VARCHAR2(100 BYTE) NULL ,
"READONLY" NUMBER(1) NULL ,
"LEVEL_MIN" NUMBER(8) NULL ,
"LEVEL_MAX" NUMBER(8) NULL ,
"ENABLED" NUMBER(1) DEFAULT 1  NULL ,
"CREATE_TIME" TIMESTAMP(6)  NULL ,
"UPDATE_TIME" TIMESTAMP(6)  NULL ,
"MODIFER" VARCHAR2(20 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
COMMENT ON COLUMN "RISK_LEVEL"."ID" IS '风险等级主键,自动递增';
COMMENT ON COLUMN "RISK_LEVEL"."LEVEL_VAL" IS '风险级别值';
COMMENT ON COLUMN "RISK_LEVEL"."NAME" IS '风险级别名称';
COMMENT ON COLUMN "RISK_LEVEL"."READONLY" IS '是否只读:1只读';
COMMENT ON COLUMN "RISK_LEVEL"."LEVEL_MIN" IS '最小风险值';
COMMENT ON COLUMN "RISK_LEVEL"."LEVEL_MAX" IS '最大风险值';
COMMENT ON COLUMN "RISK_LEVEL"."ENABLED" IS '状态:0删除,1正常,2禁用';
COMMENT ON COLUMN "RISK_LEVEL"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "RISK_LEVEL"."UPDATE_TIME" IS '最后更新时间';
COMMENT ON COLUMN "RISK_LEVEL"."MODIFER" IS '更新者';
-- ----------------------------
-- Checks structure for table RISK_LEVEL
-- ----------------------------
ALTER TABLE "RISK_LEVEL" ADD CHECK ("ID" IS NOT NULL);
-- ----------------------------
-- Primary Key structure for table RISK_LEVEL
-- ----------------------------
ALTER TABLE "RISK_LEVEL" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Table structure for ROLES
-- ----------------------------
CREATE TABLE "ROLES" (
"ID" NUMBER(8) NOT NULL ,
"ROLE_NAME" VARCHAR2(50 BYTE) NOT NULL ,
"ROLE_DESC" VARCHAR2(200 BYTE) NULL ,
"ENABLED" NUMBER(1) DEFAULT 1  NOT NULL ,
"CREATE_TIME" TIMESTAMP(6)  NULL ,
"UPDATE_TIME" TIMESTAMP(6)  NULL ,
"MODIFER" VARCHAR2(20 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
COMMENT ON COLUMN "ROLES"."ID" IS '角色ID,主键,自动递增';
COMMENT ON COLUMN "ROLES"."ROLE_NAME" IS '角色名称';
COMMENT ON COLUMN "ROLES"."ROLE_DESC" IS '角色描述';
COMMENT ON COLUMN "ROLES"."ENABLED" IS '状态:0删除,1正常,2禁用';
COMMENT ON COLUMN "ROLES"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "ROLES"."UPDATE_TIME" IS '更新时间';
COMMENT ON COLUMN "ROLES"."MODIFER" IS '更新者';
-- ----------------------------
-- Checks structure for table ROLES
-- ----------------------------
ALTER TABLE "ROLES" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "ROLES" ADD CHECK ("ROLE_NAME" IS NOT NULL);
ALTER TABLE "ROLES" ADD CHECK ("ENABLED" IS NOT NULL);
ALTER TABLE "ROLES" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "ROLES" ADD CHECK ("ROLE_NAME" IS NOT NULL);
ALTER TABLE "ROLES" ADD CHECK ("ENABLED" IS NOT NULL);
-- ----------------------------
-- Primary Key structure for table ROLES
-- ----------------------------
ALTER TABLE "ROLES" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Table structure for ROLES_RESOURCES
-- ----------------------------
CREATE TABLE "ROLES_RESOURCES" (
"ID" NUMBER(8) NOT NULL ,
"ROLES_ID" NUMBER(8) NOT NULL ,
"RESOURCES_ID" NUMBER(8) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
COMMENT ON COLUMN "ROLES_RESOURCES"."ID" IS '主键';
COMMENT ON COLUMN "ROLES_RESOURCES"."ROLES_ID" IS '角色表的外键,角色ID';
COMMENT ON COLUMN "ROLES_RESOURCES"."RESOURCES_ID" IS '资源表的外键,资源ID';
-- ----------------------------
-- Checks structure for table ROLES_RESOURCES
-- ----------------------------
ALTER TABLE "ROLES_RESOURCES" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "ROLES_RESOURCES" ADD CHECK ("ROLES_ID" IS NOT NULL);
ALTER TABLE "ROLES_RESOURCES" ADD CHECK ("RESOURCES_ID" IS NOT NULL);
ALTER TABLE "ROLES_RESOURCES" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "ROLES_RESOURCES" ADD CHECK ("ROLES_ID" IS NOT NULL);
ALTER TABLE "ROLES_RESOURCES" ADD CHECK ("RESOURCES_ID" IS NOT NULL);
-- ----------------------------
-- Primary Key structure for table ROLES_RESOURCES
-- ----------------------------
ALTER TABLE "ROLES_RESOURCES" ADD PRIMARY KEY ("ID");


-- ----------------------------
-- Table structure for SYSTEM_CONFIG
-- ----------------------------
CREATE TABLE "SYSTEM_CONFIG" (
"ID" NUMBER(8) NOT NULL ,
"TYPE" NUMBER(16) NOT NULL ,
"TYPE_NAME" VARCHAR2(255 BYTE) NULL ,
"CODE" VARCHAR2(64 BYTE) NULL ,
"VALUE" VARCHAR2(64 BYTE) NULL ,
"ENABLED" NUMBER(1) NULL ,
"REMARK" VARCHAR2(255 BYTE) NULL ,
"ORDER_BY" NUMBER(8) NULL ,
"CREATE_TIME" TIMESTAMP(6)  NULL ,
"UPDATE_TIME" TIMESTAMP(6)  NULL ,
"MODIFER" VARCHAR2(20 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
COMMENT ON COLUMN "SYSTEM_CONFIG"."ID" IS '系统配置ID,主键自动递增';
COMMENT ON COLUMN "SYSTEM_CONFIG"."TYPE" IS '类型';
COMMENT ON COLUMN "SYSTEM_CONFIG"."TYPE_NAME" IS '类型名称';
COMMENT ON COLUMN "SYSTEM_CONFIG"."CODE" IS '代码';
COMMENT ON COLUMN "SYSTEM_CONFIG"."VALUE" IS '名称';
COMMENT ON COLUMN "SYSTEM_CONFIG"."ENABLED" IS '状态:0删除,1正常,2禁用';
COMMENT ON COLUMN "SYSTEM_CONFIG"."REMARK" IS '备注';
COMMENT ON COLUMN "SYSTEM_CONFIG"."ORDER_BY" IS '排序字段，用于列表或下拽时的排序';
COMMENT ON COLUMN "SYSTEM_CONFIG"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYSTEM_CONFIG"."UPDATE_TIME" IS '更新时间';
COMMENT ON COLUMN "SYSTEM_CONFIG"."MODIFER" IS '更新者';
-- ----------------------------
-- Checks structure for table SYSTEM_CONFIG
-- ----------------------------
ALTER TABLE "SYSTEM_CONFIG" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "SYSTEM_CONFIG" ADD CHECK ("TYPE" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SYSTEM_CONFIG
-- ----------------------------
ALTER TABLE "SYSTEM_CONFIG" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Table structure for USERS
-- ----------------------------
CREATE TABLE "USERS" (
"ID" NUMBER(8) NOT NULL ,
"USER_NAME" VARCHAR2(50 BYTE) NOT NULL ,
"PASSWORD" VARCHAR2(64 BYTE) NOT NULL ,
"GROUP_NAME" VARCHAR2(50 BYTE) NULL ,
"REAL_NAME" VARCHAR2(50 BYTE) NULL ,
"TITLE" VARCHAR2(50 BYTE) NULL ,
"ENABLED" NUMBER(1) DEFAULT 1  NULL ,
"EMAIL" VARCHAR2(64 BYTE) NULL ,
"MOBILE" VARCHAR2(20 BYTE) NULL ,
"WORK_STATUS" VARCHAR2(1 BYTE) NULL ,
"DEPT_ID" NUMBER(15) NULL ,
"CREATE_TIME" TIMESTAMP(6)  NULL ,
"UPDATE_TIME" TIMESTAMP(6)  NULL ,
"MODIFER" VARCHAR2(20 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
COMMENT ON COLUMN "USERS"."ID" IS '用户ID,主键自动递增';
COMMENT ON COLUMN "USERS"."USER_NAME" IS '用户姓名';
COMMENT ON COLUMN "USERS"."PASSWORD" IS '用户密码';
COMMENT ON COLUMN "USERS"."GROUP_NAME" IS '用户组名称';
COMMENT ON COLUMN "USERS"."REAL_NAME" IS '真实姓名';
COMMENT ON COLUMN "USERS"."TITLE" IS '头衔';
COMMENT ON COLUMN "USERS"."ENABLED" IS '状态:0删除,1正常,2禁用';
COMMENT ON COLUMN "USERS"."EMAIL" IS '邮箱';
COMMENT ON COLUMN "USERS"."MOBILE" IS '手机号码';
COMMENT ON COLUMN "USERS"."WORK_STATUS" IS '工作状态';
COMMENT ON COLUMN "USERS"."DEPT_ID" IS '机构ID';
COMMENT ON COLUMN "USERS"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "USERS"."UPDATE_TIME" IS '更新时间';
COMMENT ON COLUMN "USERS"."MODIFER" IS '更新者';
-- ----------------------------
-- Checks structure for table USERS
-- ----------------------------
ALTER TABLE "USERS" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "USERS" ADD CHECK ("USER_NAME" IS NOT NULL);
ALTER TABLE "USERS" ADD CHECK ("PASSWORD" IS NOT NULL);
ALTER TABLE "USERS" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "USERS" ADD CHECK ("USER_NAME" IS NOT NULL);
ALTER TABLE "USERS" ADD CHECK ("PASSWORD" IS NOT NULL);
-- ----------------------------
-- Primary Key structure for table USERS
-- ----------------------------
ALTER TABLE "USERS" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Table structure for USERS_ROLES
-- ----------------------------
CREATE TABLE "USERS_ROLES" (
"ID" NUMBER(8) NOT NULL ,
"USERS_ID" NUMBER(8) NOT NULL ,
"ROLES_ID" NUMBER(8) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
COMMENT ON COLUMN "USERS_ROLES"."ID" IS '主键';
COMMENT ON COLUMN "USERS_ROLES"."USERS_ID" IS '用户表的外键,用户ID';
COMMENT ON COLUMN "USERS_ROLES"."ROLES_ID" IS '角色表的外键,角色ID';
-- ----------------------------
-- Checks structure for table USERS_ROLES
-- ----------------------------
ALTER TABLE "USERS_ROLES" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "USERS_ROLES" ADD CHECK ("USERS_ID" IS NOT NULL);
ALTER TABLE "USERS_ROLES" ADD CHECK ("ROLES_ID" IS NOT NULL);
ALTER TABLE "USERS_ROLES" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "USERS_ROLES" ADD CHECK ("USERS_ID" IS NOT NULL);
ALTER TABLE "USERS_ROLES" ADD CHECK ("ROLES_ID" IS NOT NULL);
-- ----------------------------
-- Primary Key structure for table USERS_ROLES
-- ----------------------------
ALTER TABLE "USERS_ROLES" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Sequence structure for DIMENSION_SEQ
-- ----------------------------
CREATE SEQUENCE "DIMENSION_SEQ"
 INCREMENT BY 1
 MINVALUE 1
 MAXVALUE 9999999999999999999999999999
 START WITH 21
 CACHE 20;

-- ----------------------------
-- Sequence structure for LOGGING_EVENT_ID_SEQ
-- ----------------------------
CREATE SEQUENCE "LOGGING_EVENT_ID_SEQ"
 INCREMENT BY 1
 MINVALUE 1
 MAXVALUE 9999999999999999999999999999
 START WITH 140
 CACHE 20;

-- ----------------------------
-- Sequence structure for RESOURCES_SEQ
-- ----------------------------
CREATE SEQUENCE "RESOURCES_SEQ"
 INCREMENT BY 1
 MINVALUE 1
 MAXVALUE 999999999999999999999999999
 START WITH 81
 CACHE 20;

-- ----------------------------
-- Sequence structure for RISK_LEVEL_SEQ
-- ----------------------------
CREATE SEQUENCE "RISK_LEVEL_SEQ"
 INCREMENT BY 1
 MINVALUE 1
 MAXVALUE 9999999999999999999999999999
 START WITH 21
 CACHE 20;

-- ----------------------------
-- Sequence structure for ROLES_RESOURCES_SEQ
-- ----------------------------
CREATE SEQUENCE "ROLES_RESOURCES_SEQ"
 INCREMENT BY 1
 MINVALUE 1
 MAXVALUE 9999999999999999999999999999
 START WITH 341
 CACHE 20;

-- ----------------------------
-- Sequence structure for ROLES_SEQ
-- ----------------------------
CREATE SEQUENCE "ROLES_SEQ"
 INCREMENT BY 1
 MINVALUE 1
 MAXVALUE 99999999999999999
 START WITH 21
 CACHE 20;

-- ----------------------------
-- Sequence structure for SYSTEM_CONFIG_SEQ
-- ----------------------------
CREATE SEQUENCE "SYSTEM_CONFIG_SEQ"
 INCREMENT BY 1
 MINVALUE 1
 MAXVALUE 999999999999999999999
 START WITH 1
 CACHE 20;
 
-- ----------------------------
-- Sequence structure for USERS_ROLES_SEQ
-- ----------------------------
CREATE SEQUENCE "USERS_ROLES_SEQ"
 INCREMENT BY 1
 MINVALUE 1
 MAXVALUE 99999999999999999999999
 START WITH 61
 CACHE 20;

-- ----------------------------
-- Sequence structure for USERS_SEQ
-- ----------------------------
CREATE SEQUENCE "USERS_SEQ"
 INCREMENT BY 1
 MINVALUE 1
 MAXVALUE 999999999999999
 START WITH 21
 CACHE 20;
 
 
-- ----------------------------
-- Triggers structure for table DIMENSION
-- ----------------------------
CREATE OR REPLACE TRIGGER "TRG_DIMENSION"
BEFORE INSERT ON "DIMENSION"
FOR EACH ROW
begin       
	select DIMENSION_SEQ.NEXTVAL into :new.ID from dual;      
end ;

-- ----------------------------
-- Triggers structure for table LOGGING_EVENT
-- ----------------------------
CREATE OR REPLACE TRIGGER "LOGGING_EVENT_ID_SEQ_TRIG" 
	BEFORE INSERT ON "LOGGING_EVENT" 
	REFERENCING OLD AS "OLD" NEW AS "NEW" FOR EACH ROW ENABLE
BEGIN  
    SELECT logging_event_id_seq.NEXTVAL 
    INTO   :NEW.event_id 
    FROM   DUAL;  
  END;
-- ----------------------------

-- ----------------------------
-- Triggers structure for table RESOURCES
-- ----------------------------
CREATE OR REPLACE TRIGGER "TRG_RESOURCES" 
BEFORE INSERT ON "RESOURCES"
FOR EACH ROW
begin       
	select RESOURCES_SEQ.NEXTVAL into :new.ID from dual;      
end;

-- ----------------------------
-- Triggers structure for table RISK_LEVEL
-- ----------------------------
CREATE OR REPLACE TRIGGER "TRG_RISK_LEVEL" 
BEFORE INSERT ON "RISK_LEVEL" 
FOR EACH ROW
begin       
	select RISK_LEVEL_SEQ.NEXTVAL into :new.ID from dual;      
end;

-- ----------------------------
-- Triggers structure for table ROLES
-- ----------------------------
CREATE OR REPLACE TRIGGER "TRG_ROLES" 
BEFORE INSERT ON "ROLES" 
FOR EACH ROW
begin       
	select ROLES_SEQ.NEXTVAL into :new.ID from dual;      
end;

-- ----------------------------
-- Triggers structure for table ROLES_RESOURCES
-- ----------------------------
CREATE OR REPLACE TRIGGER "TRG_ROLES_RESOURCES" 
BEFORE INSERT ON "ROLES_RESOURCES"
FOR EACH ROW
begin       
	select ROLES_RESOURCES_SEQ.NEXTVAL into :new.ID from dual;      
end;

-- ----------------------------
-- Triggers structure for table SYSTEM_CONFIG
-- ----------------------------
CREATE OR REPLACE TRIGGER "TRG_SYSTEM_CONFIG" 
BEFORE INSERT ON "SYSTEM_CONFIG"
FOR EACH ROW
begin       
	select SYSTEM_CONFIG_SEQ.NEXTVAL into :new.ID from dual;      
end;

-- ----------------------------
-- Triggers structure for table USERS
-- ----------------------------
CREATE OR REPLACE TRIGGER "TRG_USERS" 
BEFORE INSERT ON "USERS"
FOR EACH ROW
begin       
	select USERS_SEQ.NEXTVAL into :new.ID from dual;      
end;

-- ----------------------------
-- Triggers structure for table USERS_ROLES
-- ----------------------------
CREATE OR REPLACE TRIGGER "TRG_USERS_ROLES" 
BEFORE INSERT ON "USERS_ROLES"
FOR EACH ROW
begin       
	select USERS_ROLES_SEQ.NEXTVAL into :new.ID from dual;      
end;