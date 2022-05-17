package com.yunseojin.MyLittleHomepage.post.service;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.service.MemberServiceImpl;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceImplTest {
    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private PostServiceImpl postService;

    private static MemberRequest loginReq;
    private static MemberRequest loginReq2;
    private static final Long testBoardId = 1L;

    @BeforeAll
    static void setup() {
        loginReq = MemberRequest.builder()
                .loginId("testuser")
                .password("1234")
                .nickname("testuser")
                .build();
        loginReq2 = MemberRequest.builder()
                .loginId("testuser2")
                .password("1234")
                .nickname("testuser")
                .build();
    }

    @Test
    void createPost() {
        //given
        var req = PostRequest.builder()
                .title("제목")
                .content("내용")
                .hashTags(new String[]{"태그1", "태그2"})
                .build();

        //로그인 안됨
        assertEquals(ErrorMessage.NOT_LOGIN_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> postService.createPost(testBoardId, req)
                ).getCode());
        memberService.resister(loginReq);
        //게시판 없음
        assertEquals(ErrorMessage.NOT_EXISTS_BOARD_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> postService.createPost(0L, req)
                ).getCode());
        var postRes = postService.createPost(testBoardId, req);
        //then
        assertEquals(postRes.getBoardId(), testBoardId);
        assertEquals(postRes.getTitle(), req.getTitle());
        assertEquals(postRes.getContent(), req.getContent());
        assertArrayEquals(postRes.getHashtags(), req.getHashTags());
        memberService.logout();
    }

    @Test
    void updatePost() {
        //given
        var createReq = PostRequest.builder()
                .title("제목")
                .content("내용")
                .hashTags(new String[]{"태그1", "태그2"})
                .build();
        var updateReq = PostRequest.builder()
                .title("제목2")
                .content("내용2")
                .hashTags(new String[]{"태그2"})
                .build();

        //로그인 안됨
        assertEquals(ErrorMessage.NOT_LOGIN_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> postService.updatePost(0L, createReq)
                ).getCode());

        //다른 유저로 게시글 생성
        memberService.resister(loginReq2);
        var createRes1 = postService.createPost(testBoardId, createReq);
        memberService.logout();
        memberService.resister(loginReq);

        //게시글 없음
        assertEquals(ErrorMessage.NOT_EXISTS_POST_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> postService.updatePost(0L, createReq)
                ).getCode());
        //소유권 없음
        assertEquals(ErrorMessage.POST_PERMISSION_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> postService.updatePost(createRes1.getId(), updateReq)
                ).getCode());

        var createRes2 = postService.createPost(testBoardId, createReq);
        var updateRes = postService.updatePost(createRes2.getId(), updateReq);
        //then
        assertEquals(updateRes.getBoardId(), testBoardId);
        assertEquals(updateRes.getTitle(), updateReq.getTitle());
        assertEquals(updateRes.getContent(), updateReq.getContent());
        assertArrayEquals(updateRes.getHashtags(), updateReq.getHashTags());

    }

    @Test
    void deletePost() {
    }

    @Test
    void getPostList() {
    }

    @Test
    void getPost() {
    }
}