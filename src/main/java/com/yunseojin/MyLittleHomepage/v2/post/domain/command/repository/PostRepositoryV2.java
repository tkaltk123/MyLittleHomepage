package com.yunseojin.MyLittleHomepage.v2.post.domain.command.repository;

import com.yunseojin.MyLittleHomepage.v2.post.domain.command.aggregete.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositoryV2 extends JpaRepository<Post, Long> {

}