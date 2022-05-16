package com.yunseojin.MyLittleHomepage.post.mapper;

import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.dto.PostResponse;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PostMapperTest {
    @Test
    void toPostResponse() {
        var member = MemberEntity.builder()
                .nickname("test")
                .build();
        var post = PostEntity.builder()
                .writer(member)
                .title("제목")
                .content("내용")
                .build();
        var tags = new String[]{"tag1","tag2"};
        post.setHashtags(tags);
        var res = PostMapper.INSTANCE.toPostResponse(post);
        assertEquals(post.getWriter().getNickname(),res.getWriterName());
        assertEquals(post.getTitle(),res.getTitle());
        assertEquals(post.getContent(),res.getContent());
    }

}