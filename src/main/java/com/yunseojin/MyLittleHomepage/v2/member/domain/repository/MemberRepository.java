package com.yunseojin.MyLittleHomepage.v2.member.domain.repository;

import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;
import com.yunseojin.MyLittleHomepage.v2.member.domain.validation.constraint.UniqueLoginId;
import com.yunseojin.MyLittleHomepage.v2.member.domain.validation.constraint.UniqueNickname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Validated
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member getByUsername(String username);

    boolean existsByUsernameAndIdIsNot(String username, Long id);

    boolean existsByNicknameAndIdIsNot(String nickname, Long id);

    @Override
    <S extends Member> S save(@UniqueLoginId @UniqueNickname S member);
}