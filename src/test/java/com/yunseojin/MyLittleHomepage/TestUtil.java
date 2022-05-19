package com.yunseojin.MyLittleHomepage;

import com.yunseojin.MyLittleHomepage.etc.exception.BaseException;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestUtil {
    public static void assertCode(int code, Executable executable) {
        assertEquals(code, assertThrows(BaseException.class, executable).getCode());
    }
}
