package com.yunseojin.MyLittleHomepage.domain.member.command.repository;

import com.yunseojin.MyLittleHomepage.domain.member.command.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Validated
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}