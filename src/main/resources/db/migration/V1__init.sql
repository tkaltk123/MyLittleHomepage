DROP TABLE IF EXISTS MEMBERS CASCADE;
CREATE TABLE MEMBERS
(
    ID          BIGINT AUTO_INCREMENT PRIMARY KEY,
    CREATED_AT  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    UPDATED_AT  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    IS_DELETED  TINYINT(1)   NOT NULL DEFAULT 0,
    LOGIN_ID    VARCHAR(20)  NOT NULL,
    PASSWORD    VARCHAR(255) NOT NULL,
    NICKNAME    VARCHAR(20)  NOT NULL,
    MEMBER_TYPE VARCHAR(50)  NOT NULL
);

DROP TABLE IF EXISTS BOARDS CASCADE;
CREATE TABLE BOARDS
(
    ID         BIGINT AUTO_INCREMENT PRIMARY KEY,
    CREATED_AT TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    UPDATED_AT TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    IS_DELETED TINYINT(1)  NOT NULL DEFAULT 0,
    NAME       VARCHAR(50) NOT NULL
);


DROP TABLE IF EXISTS BOARD_COUNTS CASCADE;
CREATE TABLE BOARD_COUNTS
(
    BOARD_ID   BIGINT PRIMARY KEY,
    POST_COUNT INT NOT NULL DEFAULT 0,
    FOREIGN KEY (BOARD_ID) REFERENCES BOARDS (ID) ON DELETE CASCADE
);

DROP TABLE IF EXISTS POSTS CASCADE;
CREATE TABLE POSTS
(
    ID          BIGINT AUTO_INCREMENT PRIMARY KEY,
    CREATED_AT  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    UPDATED_AT  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    IS_DELETED  TINYINT(1)   NOT NULL DEFAULT 0,
    BOARD_ID    BIGINT       NOT NULL,
    WRITER_ID   BIGINT       NOT NULL,
    WRITER_NAME VARCHAR(20)  NOT NULL,
    TITLE       VARCHAR(255) NOT NULL,
    CONTENT     TEXT,
    FOREIGN KEY (BOARD_ID) REFERENCES BOARDS (ID),
    FOREIGN KEY (WRITER_ID) REFERENCES MEMBERS (ID)
);

DROP TABLE IF EXISTS POST_COUNTS CASCADE;
CREATE TABLE POST_COUNTS
(
    POST_ID       BIGINT PRIMARY KEY,
    VIEW_COUNT    INT NOT NULL DEFAULT 0,
    COMMENT_COUNT INT NOT NULL DEFAULT 0,
    LIKE_COUNT    INT NOT NULL DEFAULT 0,
    DISLIKE_COUNT INT NOT NULL DEFAULT 0,
    FOREIGN KEY (POST_ID) REFERENCES POSTS (ID) ON DELETE CASCADE
);

DROP TABLE IF EXISTS COMMENTS CASCADE;
CREATE TABLE COMMENTS
(
    ID         BIGINT AUTO_INCREMENT PRIMARY KEY,
    CREATED_AT TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    UPDATED_AT TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    IS_DELETED TINYINT(1) NOT NULL DEFAULT 0,
    POST_ID    BIGINT     NOT NULL,
    WRITER_ID  BIGINT     NOT NULL,
    PARENT_ID  BIGINT,
    CONTENT    TEXT,
    FOREIGN KEY (POST_ID) REFERENCES POSTS (ID),
    FOREIGN KEY (WRITER_ID) REFERENCES MEMBERS (ID),
    FOREIGN KEY (PARENT_ID) REFERENCES COMMENTS (ID)
);

DROP TABLE IF EXISTS COMMENT_COUNTS CASCADE;
CREATE TABLE COMMENT_COUNTS
(
    COMMENT_ID    BIGINT PRIMARY KEY,
    LIKE_COUNT    INT NOT NULL DEFAULT 0,
    DISLIKE_COUNT INT NOT NULL DEFAULT 0,
    FOREIGN KEY (COMMENT_ID) REFERENCES COMMENTS (ID) ON DELETE CASCADE
);

DROP TABLE IF EXISTS HASHTAGS CASCADE;
CREATE TABLE HASHTAGS
(
    ID         BIGINT AUTO_INCREMENT PRIMARY KEY,
    CREATED_AT TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    UPDATED_AT TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    IS_DELETED TINYINT(1)   NOT NULL DEFAULT 0,
    POST_ID    BIGINT       NOT NULL,
    TAG        VARCHAR(255) NOT NULL,
    FOREIGN KEY (POST_ID) REFERENCES POSTS (ID)
);

DROP TABLE IF EXISTS EVALUATIONS CASCADE;
CREATE TABLE EVALUATIONS
(
    DTYPE           VARCHAR(31)  NOT NULL,
    ID              BIGINT AUTO_INCREMENT PRIMARY KEY,
    CREATED_AT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    UPDATED_AT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    IS_DELETED      TINYINT(1)   NOT NULL DEFAULT 0,
    POST_ID         BIGINT,
    COMMENT_ID      BIGINT,
    WRITER_ID       BIGINT       NOT NULL,
    EVALUATION_TYPE VARCHAR(255) NOT NULL,
    FOREIGN KEY (POST_ID) REFERENCES POSTS (ID),
    FOREIGN KEY (COMMENT_ID) REFERENCES COMMENTS (ID),
    FOREIGN KEY (WRITER_ID) REFERENCES MEMBERS (ID)
);