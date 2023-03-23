package com.yunseojin.MyLittleHomepage.domain.post.query.repository;

import com.yunseojin.MyLittleHomepage.domain.contract.query.repository.ReadOnlyRepository;
import com.yunseojin.MyLittleHomepage.domain.post.query.model.QueriedPost;
import org.springframework.stereotype.Repository;

@Repository
public interface QueriedPostRepository extends ReadOnlyRepository<QueriedPost>,
        DslQueriedPostRepository {

}