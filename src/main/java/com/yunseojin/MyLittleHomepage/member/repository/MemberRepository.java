package com.yunseojin.MyLittleHomepage.member.repository;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname);


    default MemberEntity getMember(Long memberId){
        var _member = findById(memberId);
        if (_member.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION);
        return _member.get();
    }
}