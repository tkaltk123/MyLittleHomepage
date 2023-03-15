package com.yunseojin.MyLittleHomepage.v2.auth.service;

import com.yunseojin.MyLittleHomepage.v2.auth.domain.UserDetails;
import com.yunseojin.MyLittleHomepage.v2.auth.dto.Token;
import com.yunseojin.MyLittleHomepage.v2.auth.exception.ErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.auth.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;

@RequiredArgsConstructor
public class JwtTokenProvider {

    private final String secretKey;

    private final String accessTokenName;

    private final long accessTokenExpirationMilli;

    private final String refreshTokenName;

    private final long refreshTokenExpirationMilli;

    private final AuthenticationPrincipal principal;

    public Token login(String username, String password) throws UnauthorizedException {
        UserDetails user = principal.getByUsername(username);
        validatePassword(user, password);
        return createToken(user);
    }

    private void validatePassword(UserDetails user, String password) {
        if (Objects.isNull(user) || user.isWrongPassword(password)) {
            throw new UnauthorizedException(ErrorMessage.INVALID_USERNAME_OR_PASSWORD);
        }
    }

    public Token createToken(UserDetails user) {
        return new Token(createAccessToken(user), createRefreshToken(user));
    }

    private String createAccessToken(UserDetails user) {
        return createToken(user, accessTokenName, accessTokenExpirationMilli);
    }

    private String createRefreshToken(UserDetails user) {
        return createToken(user, refreshTokenName, refreshTokenExpirationMilli);
    }

    private String createToken(UserDetails userDetails, String subject, long expirationMilli) {
        Claims claims = Jwts.claims();
        claims.put("username", userDetails.getUsername());
        claims.setSubject(subject);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationMilli);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String refresh(String bearerRefreshToken) {
        var user = getUser(bearerRefreshToken, refreshTokenName);
        return createAccessToken(user);
    }

    private UserDetails getUser(String bearerToken, String tokenName)
            throws UnauthorizedException {
        Claims body = getValidBody(bearerToken);
        if (!tokenName.equals(body.getSubject())) {
            throw new UnauthorizedException(ErrorMessage.INVALID_TOKEN);
        }
        return principal.getByUsername(body.get("username", String.class));
    }

    public UserDetails getUser(String bearerToken)
            throws UnauthorizedException {
        return getUser(bearerToken, accessTokenName);
    }

    private Claims getValidBody(String bearerToken) {
        if (isBlankOrNotBearer(bearerToken)) {
            throw new UnauthorizedException(ErrorMessage.NOT_BEARER_TOKEN);
        }
        String accessToken = bearerToken.substring(7);

        return parseBody(accessToken);
    }

    private boolean isBlankOrNotBearer(String bearerToken) {
        return Strings.isBlank(bearerToken) || !bearerToken.startsWith("Bearer ");
    }

    private Claims parseBody(String accessToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(ErrorMessage.EXPIRED_TOKEN);
        } catch (Exception e) {
            throw new UnauthorizedException(ErrorMessage.INVALID_TOKEN);
        }
    }
}
