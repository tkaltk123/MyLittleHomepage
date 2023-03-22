package com.yunseojin.MyLittleHomepage.v2.member.domain.query.repository;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.query.repository.ReadOnlyRepository;
import com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity.QueriedMember;
import org.springframework.stereotype.Repository;

@Repository
public interface QueriedMemberRepository extends ReadOnlyRepository<QueriedMember> {

    QueriedMember getByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);
}