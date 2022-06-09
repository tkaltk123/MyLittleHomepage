package com.yunseojin.MyLittleHomepage.etc.annotation;

import javax.validation.groups.Default;

public final class ValidationGroups {

    private ValidationGroups() { }
    public interface Register extends  Default{};
    public interface Create extends  Default{};
    public interface Search extends Default {};
    public interface Update extends Default {};
    public interface Delete extends Default {};
    public interface LogIn extends Default {};
}