package com.yunseojin.MyLittleHomepage.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class CookieUtil {

    public static Cookie createTokenCookie(String cookieName, Integer remainHour, String value) {

        var cookie = new Cookie(cookieName, value);
        cookie.setMaxAge(remainHour * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        //https 적용후 사용
        //cookie.setSecure(true);

        return cookie;
    }

    public static String getToken(HttpServletRequest request, String tokenName) {

        var optToken = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(tokenName))
                .findFirst();
        if (optToken.isEmpty())
            return null;

        return optToken.get().getValue();
    }
}
