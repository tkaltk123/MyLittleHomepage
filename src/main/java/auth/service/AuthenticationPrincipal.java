package auth.service;


import auth.domain.UserDetails;

public interface AuthenticationPrincipal {

    UserDetails getByUsername(String username);
}
