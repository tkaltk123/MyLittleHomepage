package com.yunseojin.MyLittleHomepage.etc.service;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.enums.TokenValidationType;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberTokenDto;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final RedisService redisService;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.name}")
    private String accessTokenName;

    @Value("${jwt.access.remain}")
    private Integer accessTokenRemainHour;

    @Value("${jwt.refresh.name}")
    private String refreshTokenName;

    @Value("${jwt.refresh.remain}")
    private Integer refreshTokenRemainHour;

    public String generateAccessToken(MemberEntity member) {

        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createPayload(member))
                .setSubject(accessTokenName)
                .setExpiration(createExpiration(accessTokenRemainHour))
                .signWith(SignatureAlgorithm.HS256, getKey())
                .compact();
    }

    public String generateRefreshToken(MemberEntity member) {

        var key = getRedisRefreshKey(member.getId());
        var refreshToken = redisService.getValues(key);

        //저장된 토큰이 있을 경우 유효성 검증
        if (refreshToken != null) {
            try {
                getPayloadsFrom(refreshToken);
            } catch (Exception e) {
                refreshToken = null;
            }
        }

        //저장된 토큰이 없을 경우 생성 후 redis에 저장
        if (refreshToken == null) {
            refreshToken = Jwts.builder()
                    .setHeader(createHeader())
                    .setSubject(refreshTokenName)
                    .setExpiration(createExpiration(refreshTokenRemainHour))
                    .signWith(SignatureAlgorithm.HS256, getKey())
                    .compact();
            redisService.setValues(key, refreshToken, Duration.ofHours(refreshTokenRemainHour));
        }

        return refreshToken;
    }

    public Claims getPayloadsFrom(String token) {

        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(token)
                .getBody();
    }

    public MemberTokenDto getMemberTokenDtoFrom(String token) {

        Claims payload;
        try {
            payload = getPayloadsFrom(token);
        } catch (Exception e) {
            return null;
        }
        return MemberTokenDto.builder()
                .id(payload.get("memberId", Long.class))
                .loginId(payload.get("loginId", String.class))
                .nickname(payload.get("nickname", String.class))
                .build();
    }

    public Long getMemberId(String token) {

        Claims payload;
        try {
            payload = getPayloadsFrom(token);
        } catch (ExpiredJwtException e) {
            payload = e.getClaims();
        } catch (Exception e) {
            return null;
        }

        return payload.get("memberId", Long.class);
    }

    public TokenValidationType validateAccessToken(String token) {

        try {

            Claims payload = getPayloadsFrom(token);
            String sub = payload.getSubject();

            if (!sub.equals(accessTokenName))
                return TokenValidationType.INVALID;

        } catch (ExpiredJwtException e) {
            return TokenValidationType.EXPIRE;
        } catch (Exception e) {
            return TokenValidationType.INVALID;
        }

        return TokenValidationType.VALID;
    }

    public boolean isValidRefreshToken(String accessToken, String refreshToken) {

        Claims payload;
        //access 토큰이 만료되지 않으면 false
        try {
            getPayloadsFrom(accessToken);
            return false;
        } catch (ExpiredJwtException e) {
            payload = e.getClaims();
        } catch (Exception e) {
            return false;
        }

        try {

            Claims refreshPayload = getPayloadsFrom(refreshToken);
            String sub = refreshPayload.getSubject();

            if (!sub.equals(refreshTokenName))
                return false;

            var memberId = payload.get("memberId", Long.class);
            var dbRefresh = redisService.getValues(getRedisRefreshKey(memberId));

            return refreshToken.equals(dbRefresh);
        } catch (Exception ignore) {
            return false;
        }
    }

    private Map<String, Object> createHeader() {

        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");

        return header;
    }

    private Map<String, Object> createPayload(MemberEntity member) {

        Map<String, Object> payload = new HashMap<>();
        payload.put("memberId", member.getId());
        payload.put("loginId", member.getLoginId());
        payload.put("nickname", member.getNickname());

        return payload;
    }

    private Date createExpiration(Integer remainHour) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, remainHour);

        return calendar.getTime();
    }

    private Key getKey() {

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private String getRedisRefreshKey(Long memberId) {

        return "refresh" + memberId;
    }
}
