DROP TABLE IF EXISTS members CASCADE;
CREATE TABLE members
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    is_deleted TINYINT(1)   NOT NULL DEFAULT 0,
    username   VARCHAR(20)  NOT NULL,
    password   VARCHAR(255) NOT NULL,
    nickname   VARCHAR(20)  NOT NULL,
    role       VARCHAR(50)  NOT NULL
);

DROP TABLE IF EXISTS boards CASCADE;
CREATE TABLE boards
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    is_deleted TINYINT(1)  NOT NULL DEFAULT 0,
    name       VARCHAR(50) NOT NULL
);


DROP TABLE IF EXISTS board_counts CASCADE;
CREATE TABLE board_counts
(
    board_id   BIGINT PRIMARY KEY,
    post_count INT NOT NULL DEFAULT 0,
    FOREIGN KEY (board_id) REFERENCES boards (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS posts CASCADE;
CREATE TABLE posts
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    is_deleted  TINYINT(1)   NOT NULL DEFAULT 0,
    board_id    BIGINT,
    writer_id   BIGINT,
    writer_name VARCHAR(20)  NOT NULL,
    title       VARCHAR(255) NOT NULL,
    content     TEXT,
    FOREIGN KEY (board_id) REFERENCES boards (id) ON DELETE SET NULL,
    FOREIGN KEY (writer_id) REFERENCES members (id) ON DELETE SET NULL
);

DROP TABLE IF EXISTS post_counts CASCADE;
CREATE TABLE post_counts
(
    post_id       BIGINT PRIMARY KEY,
    view_count    INT NOT NULL DEFAULT 0,
    comment_count INT NOT NULL DEFAULT 0,
    like_count    INT NOT NULL DEFAULT 0,
    dislike_count INT NOT NULL DEFAULT 0,
    FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS comments CASCADE;
CREATE TABLE comments
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    is_deleted  TINYINT(1)  NOT NULL DEFAULT 0,
    post_id     BIGINT,
    writer_id   BIGINT,
    writer_name VARCHAR(20) NOT NULL,
    parent_id   BIGINT,
    content     TEXT,
    FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE SET NULL,
    FOREIGN KEY (writer_id) REFERENCES members (id) ON DELETE SET NULL,
    FOREIGN KEY (parent_id) REFERENCES comments (id) ON DELETE SET NULL
);

DROP TABLE IF EXISTS comment_counts CASCADE;
CREATE TABLE comment_counts
(
    comment_id    BIGINT PRIMARY KEY,
    like_count    INT NOT NULL DEFAULT 0,
    dislike_count INT NOT NULL DEFAULT 0,
    FOREIGN KEY (comment_id) REFERENCES comments (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS hashtags CASCADE;
CREATE TABLE hashtags
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    is_deleted TINYINT(1)   NOT NULL DEFAULT 0,
    post_id    BIGINT       NOT NULL,
    tag        VARCHAR(255) NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS feedbacks CASCADE;
CREATE TABLE feedbacks
(
    dtype         VARCHAR(31)  NOT NULL,
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    is_deleted    TINYINT(1)   NOT NULL DEFAULT 0,
    post_id       BIGINT,
    comment_id    BIGINT,
    writer_id     BIGINT,
    feedback_type VARCHAR(255) NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
    FOREIGN KEY (comment_id) REFERENCES comments (id) ON DELETE CASCADE,
    FOREIGN KEY (writer_id) REFERENCES members (id) ON DELETE SET NULL
);