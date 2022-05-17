CREATE TABLE ACCOUNTS(
                         ID SERIAL PRIMARY KEY,
                         NAME VARCHAR(50) NOT NULL
);

CREATE TABLE USERS(
                      ID SERIAL PRIMARY KEY,
                      NAME VARCHAR(50) NOT NULL,
                      ACCOUNT_ID INT NOT NULL REFERENCES ACCOUNTS (ID),
                      PASSWORD VARCHAR(60) NOT NULL
);
CREATE UNIQUE INDEX USERS_ACCOUNT_ID ON USERS (NAME, ACCOUNT_ID);

CREATE TABLE PROJECTS(
                         ID SERIAL PRIMARY KEY,
                         NAME VARCHAR(50) NOT NULL,
                         ACCOUNT_ID INT NOT NULL REFERENCES ACCOUNTS (ID)
);

CREATE INDEX PROJECT_ACCOUNT_ID ON PROJECTS (ACCOUNT_ID);

CREATE TABLE ENVIRONMENTS(
                             ID SERIAL PRIMARY KEY,
                             NAME VARCHAR(50) NOT NULL,
                             PROJECT_ID INT NOT NULL REFERENCES PROJECTS (ID),
                             USER_ID INT REFERENCES USERS (ID)
);

CREATE INDEX ENVIRONMENT_PROJECT_ID ON ENVIRONMENTS (PROJECT_ID);

CREATE TABLE FEATURE_FLAGS(
                              ID SERIAL PRIMARY KEY,
                              NAME VARCHAR(50) NOT NULL,
                              ENVIRONMENT_ID INT NOT NULL REFERENCES ENVIRONMENTS (ID),
                              USER_ID INT
);

CREATE INDEX FEATURE_ENVIRONMENT_ID ON FEATURE_FLAGS (ENVIRONMENT_ID);
CREATE INDEX FEATURE_FLAGS_NAME ON FEATURE_FLAGS (NAME);