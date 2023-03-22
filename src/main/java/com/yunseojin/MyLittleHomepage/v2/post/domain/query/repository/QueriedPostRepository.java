package com.yunseojin.MyLittleHomepage.v2.post.domain.query.repository;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.query.repository.ReadOnlyRepository;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.entity.QueriedPost;
import org.springframework.stereotype.Repository;

@Repository
public interface QueriedPostRepository extends ReadOnlyRepository<QueriedPost>,
        DslQueriedPostRepository {

}