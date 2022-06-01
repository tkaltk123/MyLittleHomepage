INSERT INTO my_little_homepage.boards(name)
VALUES('test');

INSERT INTO my_little_homepage.board_counts(board_id)
VALUES (LAST_INSERT_ID());