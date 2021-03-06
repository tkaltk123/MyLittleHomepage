package com.yunseojin.MyLittleHomepage;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BaseException;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestUtil {
    public static void assertError(ErrorMessage error, Executable executable) {
        assertEquals(error.getCode(), assertThrows(BaseException.class, executable).getCode());
    }
}
