package com.yunseojin.MyLittleHomepage.etc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public String getRefreshToken(Long memberId) {

        return getValues(getRefreshKey(memberId));
    }

    public void setRefreshToken(Long memberId, String refreshToken, Integer remainHour) {

        setValues(getRefreshKey(memberId), refreshToken, Duration.ofHours(remainHour));
    }

    public boolean viewPost(String ip, Long postId) {

        var key = getViewKey(ip, postId);
        var memValue = getValues(key);
        if (memValue != null) {

            var memDate = LocalDate.parse(memValue);
            if (memDate.isEqual(LocalDate.now()))
                return false;
        }
        setValues(key, LocalDate.now().toString(), Duration.ofDays(1));

        return true;
    }

    public boolean createPost(Long memberId) {

        var key = getCreatePostKey(memberId);
        if (getValues(key) != null)
            return false;
        setValues(key, "", Duration.ofSeconds(10));

        return true;
    }

    public boolean createComment(Long memberId) {

        var key = getCreateCommentKey(memberId);
        if (getValues(key) != null)
            return false;
        setValues(key, "", Duration.ofSeconds(10));

        return true;
    }

    private void setValues(String key, String data, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    private String getValues(String key) {

        ValueOperations<String, String> values = redisTemplate.opsForValue();

        return values.get(key);
    }

    private String getRefreshKey(Long memberId) {

        return "refresh" + memberId;
    }

    private String getViewKey(String ip, Long postId) {
        return "post" + postId + "ip" + ip;
    }

    private String getCreatePostKey(Long memberId) {
        return "createPost" + memberId;
    }

    private String getCreateCommentKey(Long memberId) {
        return "createComment" + memberId;
    }
}