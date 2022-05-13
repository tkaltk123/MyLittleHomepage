package com.yunseojin.MyLittleHomepage.member.repository;

import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByLoginId(String loginId);
}