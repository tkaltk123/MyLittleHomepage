package com.yunseojin.MyLittleHomepage.v2.domain.post.query.repository;

import com.yunseojin.MyLittleHomepage.v2.domain.contract.query.repository.ReadOnlyRepository;
import com.yunseojin.MyLittleHomepage.v2.domain.post.query.model.QueriedPost;
import org.springframework.stereotype.Repository;

@Repository
public interface QueriedPostRepository extends ReadOnlyRepository<QueriedPost>,
        DslQueriedPostRepository {

}