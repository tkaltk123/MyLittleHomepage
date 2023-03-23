package com.yunseojin.MyLittleHomepage.v2.infrastructure.auth;

import com.yunseojin.MyLittleHomepage.v2.domain.auth.service.AuthService;
import com.yunseojin.MyLittleHomepage.v2.domain.auth.vo.Token;
import com.yunseojin.MyLittleHomepage.v2.domain.member.query.model.QueriedMember;
import com.yunseojin.MyLittleHomepage.v2.domain.member.query.repository.QueriedMemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
public class JwtProvider implements UserDetailsService, AuthService {

    private static final String USERNAME = "username";

    private static final String AUTHORITIES = "authorities";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.name}")
    private String accessTokenName;

    @Value("${jwt.access.exp}")
    private Integer accessTokenExp;

    @Value("${jwt.refresh.name}")
    private String refreshTokenName;

    @Value("${jwt.refresh.exp}")
    private Integer refreshTokenExp;

    private final QueriedMemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUser(repository.getByUsername(username));
    }

    private UserDetails loadUser(QueriedMember member) throws UsernameNotFoundException {
        if (Objects.isNull(member)) {
            throw new UsernameNotFoundException("사용자가 없습니다.");
        }
        return new UserDetailsImpl(member);
    }

    @Override
    public Token login(String username, String password) {
        var user = loadUserByUsername(username);
        validatePassword(user, password);

        return getToken(user.getUsername(), user.getAuthorities());
    }

    private void validatePassword(UserDetails user, String password) {
        ;
        if (Objects.isNull(user) || !(password != null && BCrypt.checkpw(password,
                user.getPassword()))) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }
    }

    private Token getToken(String id, Collection<? extends GrantedAuthority> authorities) {

        String accessToken = createAccessToken(id, authorities);
        String refreshToken = createRefreshToken(id, authorities);

        return new Token(accessToken, refreshToken);
    }

    public String createAccessToken(String id, Collection<?> authorities) {
        return createToken(id, authorities, accessTokenName, accessTokenExp);
    }

    private String createRefreshToken(String id, Collection<?> authorities) {
        return createToken(id, authorities, refreshTokenName, refreshTokenExp);
    }

    private String createToken(String id, Collection<?> authorities, String subject,
            long expirationMilli) {

        Claims claims = Jwts.claims()
                .setSubject(subject);
        claims.put(USERNAME, id);
        claims.put(AUTHORITIES, authorities);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationMilli);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    @Override
    public String getToken(String bearerToken) {

        if (Strings.isBlank(bearerToken)) {
            return null;
        }

        if (!bearerToken.startsWith("Bearer ")) {
            throw new BadCredentialsException("유효하지 않은 인증 방식입니다.");
        }

        return bearerToken.substring(7);
    }


    @Override
    public Authentication getAuthentication(String accessToken) {
        var user = loadUser(accessToken, accessTokenName);
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    @Override
    public String refresh(String refreshToken) {
        UserDetails user = loadUser(refreshToken, refreshTokenName);
        return createAccessToken(user.getUsername(), user.getAuthorities());
    }

    private UserDetails loadUser(String token, String name) {
        var body = parseBody(token);
        validateTokenName(body, name);
        String id = body.get(USERNAME, String.class);
        var member = repository.getById(Long.valueOf(id));

        return loadUser(member);
    }

    private Claims parseBody(String accessToken) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(accessToken)
                .getBody();
    }

    private void validateTokenName(Claims body, String name) {
        if (!Objects.equals(body.getSubject(), name)) {
            throw new BadCredentialsException("유효하지 않은 토큰입니다.");
        }
    }

}
