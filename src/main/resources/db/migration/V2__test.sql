INSERT INTO MEMBERS(LOGIN_ID, PASSWORD, NICKNAME, MEMBER_TYPE)
VALUES('testuser','12341234','testuser','NORMAL');

INSERT INTO BOARDS(NAME)
VALUES('test');

INSERT INTO board_counts(BOARD_ID)
VALUES (LAST_INSERT_ID());