package com.yunseojin.MyLittleHomepage.util;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;

public class SessionUtil {
    public static void checkLogin(MemberInfo memberInfo, boolean requireLoggedIn) throws BadRequestException {
        var notLoggedIn = memberInfo.getId() == null;
        if (requireLoggedIn && notLoggedIn)
            throw new BadRequestException(ErrorMessage.NOT_LOGIN_EXCEPTION);
        else if (!requireLoggedIn && !notLoggedIn)
            throw new BadRequestException(ErrorMessage.ALREADY_LOGIN_EXCEPTION);
    }
}
