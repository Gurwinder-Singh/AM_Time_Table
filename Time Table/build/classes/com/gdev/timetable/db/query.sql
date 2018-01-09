CREATE TABLE  "USERS" 
   ("ID" bigint not null generated always as identity(start with 1, increment by 1) CONSTRAINT users_pk PRIMARY KEY, 
	"NAME" VARCHAR(1000),
        "USERNAME" VARCHAR(1000),
	"PASSWORD" VARCHAR(4000), 
	"ACTIVE" CHAR(1) DEFAULT 'Y'
   );
INSERT INTO USERS(name,username, password,active) VALUES('admin','admin', 'YWRtaW4=','Y');
CREATE TABLE  "DEPARTMENTS" 
   ("ID" bigint not null generated always as identity(start with 1, increment by 1) CONSTRAINT DEPARTMENTS_PK PRIMARY KEY, 
	"NAME" VARCHAR(1000)
   );
CREATE TABLE  "BARANCHS" 
   ("ID" bigint not null generated always as identity(start with 1, increment by 1) CONSTRAINT BARANCHS_PK PRIMARY KEY, 
	"NAME" VARCHAR(1000)
   );

CREATE TABLE  "TEACHER" 
   ("ID" bigint not null generated always as identity(start with 1, increment by 1) CONSTRAINT TEACHER_PK PRIMARY KEY, 
	"NAME" VARCHAR(1000),
        "TEACH_NO" VARCHAR(1000)
   );

CREATE TABLE "SUBJECTS"
  ("ID" BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1) CONSTRAINT SUBJECTS_PK PRIMARY KEY,
   "NAME" VARCHAR(1000),
   "SUB_ID" VARCHAR(200),
   "DEP_ID" BIGINT,
   "BRANCH_ID" BIGINT,
   "TYPE" CHAR(2)
  );

CREATE TABLE "SUB_ALLOC"
("ID" BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1) CONSTRAINT SUB_ALLOC_PK PRIMARY KEY,
 "SUB_ID" BIGINT,
 "TEACH_ID" BIGINT,
 "TYPE" CHAR(2),
 "DEP_ID" BIGINT,
 "BRANCH_ID" BIGINT,
 "SEM" BIGINT,
 "LOAD" BIGINT,
 "LENGTH" BIGINT,
 "SESSION" VARCHAR(200),
 "PRIORITY" INT
);

CREATE TABLE "TIME_TABLE"
("ID" BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1) CONSTRAINT TIME_TABLE_PK PRIMARY KEY,
 "SESSION" VARCHAR(200),
 "DAY" BIGINT,
 "SUB_ALLOC_ID" BIGINT,
 "LEC" BIGINT
);

CREATE TABLE "CONFIG" 
("ID" BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1) CONSTRAINT CONFIG_PK PRIMARY KEY,
 "DEP_ID" BIGINT,
 "BRANCH_ID" BIGINT,
 "SEM" BIGINT,
 "TOTAL_LEC" BIGINT,
 "TOTAL_DAY" BIGINT
);