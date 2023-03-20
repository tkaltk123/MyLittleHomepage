package com.yunseojin.MyLittleHomepage.v2.comment.domain.command.repository;

import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.aggregete.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepositoryV2 extends JpaRepository<Comment, Long> {

}