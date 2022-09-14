package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.enums.MemberType;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.JwtTokenResponse;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.mapper.MemberMapper;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.etc.service.JwtService;
import com.yunseojin.MyLittleHomepage.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    @Override
    public JwtTokenResponse register(MemberRequest memberRequest) {

        checkDuplication(memberRequest);

        var member = createMemberEntity(memberRequest);

        memberRepository.save(member);

        var accessToken = jwtService.generateAccessToken(member);
        var refreshToken = jwtService.generateRefreshToken(member);

        return createTokenResponse(accessToken, refreshToken);
    }

    @Override
    public String modify(Long memberId, MemberRequest memberRequest) {

        var member = getMemberById(memberId);

        PasswordUtil.checkPassword(memberRequest.getCurrentPassword(), member.getPassword());
        checkDuplicationFor(member, memberRequest);

        member.update(memberRequest);

        return jwtService.generateAccessToken(member);
    }

    @Override
    public void delete(Long memberId, MemberRequest memberRequest) {

        var member = getMemberById(memberId);

        PasswordUtil.checkPassword(memberRequest.getCurrentPassword(), member.getPassword());

        memberRepository.delete(member);
        member.setIsDeleted(1);
    }

    @Override
    public JwtTokenResponse login(MemberRequest memberRequest) {

        MemberEntity member = getLoginMember(memberRequest);

        var accessToken = jwtService.generateAccessToken(member);
        var refreshToken = jwtService.generateRefreshToken(member);

        return JwtTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public String refreshAccessToken(String accessToken, String refreshToken) {

        var memberId = jwtService.getMemberId(accessToken);
        var member = getMemberById(memberId);

        if (!jwtService.isValidRefreshToken(accessToken, refreshToken))
            throw new BadRequestException(ErrorMessage.INVALID_TOKEN_EXCEPTION);

        return jwtService.generateAccessToken(member);
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

    private MemberEntity createMemberEntity(MemberRequest memberRequest) {

        return MemberMapper.INSTANCE.toMemberEntity(memberRequest)
                .toBuilder()
                .password(memberRequest.getPassword())
                .memberType(MemberType.NORMAL)
                .build();
    }

    private JwtTokenResponse createTokenResponse(String accessToken, String refreshToken) {

        return JwtTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
