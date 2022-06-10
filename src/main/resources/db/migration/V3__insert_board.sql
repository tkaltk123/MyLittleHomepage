DELETE FROM my_little_homepage.boards
WHERE name = 'test';

INSERT INTO my_little_homepage.boards(name)
VALUES('자유 게시판');

INSERT INTO my_little_homepage.board_counts(board_id)
VALUES (LAST_INSERT_ID());

INSERT INTO my_little_homepage.boards(name)
VALUES('유머 게시판');

INSERT INTO my_little_homepage.board_counts(board_id)
VALUES (LAST_INSERT_ID());
