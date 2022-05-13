package com.yunseojin.MyLittleHomepage.member.repository;

import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.util.PasswordUtil;
import com.yunseojin.MyLittleHomepage.util.enumtype.MemberType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("저장")
    public void save() {
        //given
        var member = MemberEntity.builder()
                .loginId("login_id")
                .password(PasswordUtil.getHashedPassword("1234"))
                .nickname("pungun")
                .memberType(MemberType.ADMIN)
                .build();

        //when
        var dbMember = memberRepository.save(member);

        // then
        assertEquals(member.getLoginId(), dbMember.getLoginId());
        assertTrue(PasswordUtil.checkPassword("1234", dbMember.getPassword()));
        assertEquals(member.getNickname(), dbMember.getNickname());
        assertEquals(member.getMemberType(), dbMember.getMemberType());
    }

    @Test
    @DisplayName("로그인 ID로 찾기")
    public void findByLoginId() {
        //given
        var member = MemberEntity.builder()
                .loginId("login_id")
                .password(PasswordUtil.getHashedPassword("1234"))
                .nickname("pungun")
                .memberType(MemberType.ADMIN)
                .build();

        //when
        memberRepository.save(member);
        var dbMember = memberRepository.findByLoginId(member.getLoginId());

        // then
        assertEquals(member.getLoginId(), dbMember.getLoginId());
        assertTrue(PasswordUtil.checkPassword("1234", dbMember.getPassword()));
        assertEquals(member.getNickname(), dbMember.getNickname());
        assertEquals(member.getMemberType(), dbMember.getMemberType());
    }

    @Test
    @DisplayName("수정")
    public void update() {
        //given
        var member = MemberEntity.builder()
                .loginId("login_id")
                .password(PasswordUtil.getHashedPassword("1234"))
                .nickname("pungun")
                .memberType(MemberType.ADMIN)
                .build();

        //when
        memberRepository.save(member);
        member.setLoginId("login_id2");
        member.setPassword(PasswordUtil.getHashedPassword("5678"));
        member.setNickname("pungun2");
        member.setMemberType(MemberType.NORMAL);
        var dbMember = memberRepository.findById(member.getId()).get();

        // then
        assertEquals("login_id2", dbMember.getLoginId());
        assertTrue(PasswordUtil.checkPassword("5678", dbMember.getPassword()));
        assertEquals("pungun2", dbMember.getNickname());
        assertEquals(MemberType.NORMAL, dbMember.getMemberType());
    }

    @Test
    @DisplayName("삭제")
    public void delete() {
        //given
        var member = MemberEntity.builder()
                .loginId("login_id")
                .password(PasswordUtil.getHashedPassword("1234"))
                .nickname("pungun")
                .memberType(MemberType.ADMIN)
                .build();

        //when
        memberRepository.save(member);
        memberRepository.delete(member);
        var dbMember = memberRepository.findById(member.getId());
        var dbMember2 = memberRepository.findByLoginId(member.getLoginId());

        // then
        assertEquals(Optional.empty(), dbMember);
        assertNull(dbMember2);
    }
}