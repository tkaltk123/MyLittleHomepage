package com.yunseojin.MyLittleHomepage.v2.post.domain.query.repository;

import com.yunseojin.MyLittleHomepage.v2.post.domain.query.entity.QueriedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchedPostRepository extends JpaRepository<QueriedPost, Long>,
        DslSearchedPostRepository {

}