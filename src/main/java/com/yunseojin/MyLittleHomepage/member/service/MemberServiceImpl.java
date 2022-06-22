package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.enums.MemberType;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.dto.MemberResponse;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.mapper.MemberMapper;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberInfo memberInfo;
    private final MemberRepository memberRepository;

    @Override
    @Login(required = false)
    public MemberResponse register(MemberRequest memberRequest) {

        checkDuplication(memberRequest);

        var member = MemberMapper.INSTANCE.toMemberEntity(memberRequest)
                .toBuilder()
                .memberType(MemberType.NORMAL)
                .build();

        memberRepository.save(member);
        memberInfo.setMember(member);

        return MemberMapper.INSTANCE.toMemberResponse(member);
    }

    @Override
    @Login
    public MemberResponse modify(MemberRequest memberRequest) {

        var member = getMemberById(memberInfo.getId());

        PasswordUtil.checkPassword(memberRequest.getCurrentPassword(), member.getPassword());
        checkDuplicationFor(member, memberRequest);

        member.update(memberRequest);
        memberInfo.setMember(member);

        return MemberMapper.INSTANCE.toMemberResponse(member);
    }

    @Login
    @Override
    public void delete(MemberRequest memberRequest) {

        var member = getMemberById(memberInfo.getId());

        PasswordUtil.checkPassword(memberRequest.getCurrentPassword(), member.getPassword());

        memberRepository.delete(member);
        member.setIsDeleted(1);
        memberInfo.clear();
    }

    @Login(required = false)
    @Override
    public MemberResponse login(MemberRequest memberRequest) {

        MemberEntity member = getLoginMember(memberRequest);

        memberInfo.setMember(member);

        return MemberMapper.INSTANCE.toMemberResponse(member);
    }

    @Login
    @Override
    public void logout() {
        memberInfo.clear();
    }

    private void checkDuplication(MemberRequest memberRequest) {

        if (memberRepository.existsByLoginId(memberRequest.getLoginId()))
            throw new BadRequestException(ErrorMessage.LOGIN_ID_DUPLICATE_EXCEPTION);

        if (memberRepository.existsByNickname(memberRequest.getNickname()))
            throw new BadRequestException(ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION);
    }

    private void checkDuplicationFor(MemberEntity member, MemberRequest memberRequest) {

        var newLoginId = memberRequest.getLoginId();
        var newNickname = memberRequest.getNickname();

        if (newLoginId != null
                && !member.getLoginId().equals(newLoginId)
                && memberRepository.existsByLoginId(newLoginId))
            throw new BadRequestException(ErrorMessage.LOGIN_ID_DUPLICATE_EXCEPTION);

        if (newNickname != null
                && !member.getNickname().equals(newNickname)
                && memberRepository.existsByNickname(newNickname))
            throw new BadRequestException(ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION);
    }

    public MemberEntity getLoginMember(MemberRequest memberRequest) {

        try {

            var member = getMemberByLoginId(memberRequest.getLoginId());

            PasswordUtil.checkPassword(memberRequest.getPassword(), member.getPassword());

            return member;
        } catch (Exception ignore) {
            throw new BadRequestException(ErrorMessage.LOGIN_FAILED_EXCEPTION);
        }
    }

    private MemberEntity getMemberById(Long memberId) {

        var optMember = memberRepository.findById(memberId);

        if (optMember.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION);

        return optMember.get();
    }

    private MemberEntity getMemberByLoginId(String loginId) {

        var optMember = memberRepository.findByLoginId(loginId);

        if (optMember.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION);

        return optMember.get();
    }
}
