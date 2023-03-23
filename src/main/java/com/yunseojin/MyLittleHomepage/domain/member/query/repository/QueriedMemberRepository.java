package com.yunseojin.MyLittleHomepage.domain.member.query.repository;

import com.yunseojin.MyLittleHomepage.domain.contract.query.repository.ReadOnlyRepository;
import com.yunseojin.MyLittleHomepage.domain.member.query.model.QueriedMember;
import org.springframework.stereotype.Repository;

@Repository
public interface QueriedMemberRepository extends ReadOnlyRepository<QueriedMember> {

    QueriedMember getByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);
}