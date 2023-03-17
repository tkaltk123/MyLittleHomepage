package com.yunseojin.MyLittleHomepage.v2.member.domain.command.repository;

import com.yunseojin.MyLittleHomepage.v2.member.domain.command.aggregate.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Validated
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}