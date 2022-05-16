package com.yunseojin.MyLittleHomepage.util;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {
    public static String getHashedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static void checkPassword(String password, String dbPassword) {
        if (!BCrypt.checkpw(password, dbPassword))
            throw new BadRequestException(ErrorMessage.INCORRECT_PASSWORD_EXCEPTION);
    }
}
