package com.yunseojin.MyLittleHomepage.v2.member.domain.command.validation;

import com.yunseojin.MyLittleHomepage.v2.member.domain.command.exception.MemberErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.exception.MemberException;
import com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity.QueriedMember;
import com.yunseojin.MyLittleHomepage.v2.member.domain.query.repository.QueriedMemberRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final QueriedMemberRepository repository;


    public void validateNickname(QueriedMember member, String exclude) {
        if (duplicatedNickname(member.getNickname(), exclude)) {
            throw new MemberException(MemberErrorMessage.NICKNAME_DUPLICATE_EXCEPTION);
        }
    }

    private boolean duplicatedNickname(String nickname, String exclude) {
        return notEquals(nickname, exclude) && repository.existsByNickname(nickname);
    }

    private boolean notEquals(String str1, String str2) {
        return !Objects.equals(str1, str2);
    }

    public void validateUsername(QueriedMember member, String exclude) {
        if (duplicatedUsername(member.getUsername(), exclude)) {
            throw new MemberException(MemberErrorMessage.USERNAME_DUPLICATE_EXCEPTION);
        }
    }

    private boolean duplicatedUsername(String username, String exclude) {
        return notEquals(username, exclude) && repository.existsByUsername(username);
    }

    public void validatePassword(QueriedMember member, String password) {
        if (member.isWrongPassword(password)) {
            throw new MemberException(MemberErrorMessage.INCORRECT_PASSWORD_EXCEPTION);
        }
    }
}
