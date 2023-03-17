package com.yunseojin.MyLittleHomepage.v2.post.domain.repository;

import com.yunseojin.MyLittleHomepage.v2.post.domain.SearchedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchedPostRepository extends JpaRepository<SearchedPost, Long>,
        DslSearchedPostRepository {

}