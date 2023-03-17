package com.yunseojin.MyLittleHomepage.v2.member.domain.query.repository;

import com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity.QueriedMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Validated
@Repository
public interface QueriedMemberRepository extends JpaRepository<QueriedMember, Long> {

    QueriedMember getByUsername(String username);

    boolean existsByUsernameAndIdIsNot(String username, Long id);

    boolean existsByNicknameAndIdIsNot(String nickname, Long id);
}