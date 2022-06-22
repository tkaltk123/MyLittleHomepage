package com.yunseojin.MyLittleHomepage.post.repository;

import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>, DslPostRepository {
}