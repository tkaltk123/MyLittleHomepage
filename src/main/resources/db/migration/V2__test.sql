INSERT INTO my_little_homepage.BOARDS(NAME)
VALUES('test');

INSERT INTO my_little_homepage.BOARD_COUNTS(BOARD_ID)
VALUES (LAST_INSERT_ID());