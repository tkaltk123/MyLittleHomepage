package com.yunseojin.MyLittleHomepage.v2.auth.service;


import com.yunseojin.MyLittleHomepage.v2.auth.domain.UserDetails;

public interface AuthenticationPrincipal {

    UserDetails getByUsername(String username);
}
