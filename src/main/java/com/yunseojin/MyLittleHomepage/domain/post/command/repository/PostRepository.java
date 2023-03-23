package com.yunseojin.MyLittleHomepage.domain.post.command.repository;

import com.yunseojin.MyLittleHomepage.domain.post.command.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}