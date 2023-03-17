package com.yunseojin.MyLittleHomepage.v2.member.domain.repository;

import com.yunseojin.MyLittleHomepage.v2.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Validated
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member getByUsername(String username);

    boolean existsByUsernameAndIdIsNot(String username, Long id);

    boolean existsByNicknameAndIdIsNot(String nickname, Long id);
}