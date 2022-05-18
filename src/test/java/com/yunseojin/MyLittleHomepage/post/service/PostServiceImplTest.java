package com.yunseojin.MyLittleHomepage.post.service;

import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.service.MemberServiceImpl;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceImplTest {
    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BoardRepository boardRepository;

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
                .nickname("testuser2")
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
        var board = boardRepository.findById(testBoardId).get();
        var postSize = board.getPostCount();

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
        assertEquals(board.getPostCount(), postSize + 1);
        assertEquals(postRes.getBoardId(), testBoardId);
        assertEquals(postRes.getTitle(), req.getTitle());
        assertEquals(postRes.getContent(), req.getContent());
        assertArrayEquals(postRes.getHashtags(), req.getHashTags());
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
        //given
        var createReq = PostRequest.builder()
                .title("제목")
                .content("내용")
                .hashTags(new String[]{"태그1", "태그2"})
                .build();
        var board = boardRepository.findById(testBoardId).get();
        var postSize = board.getPostCount();
        
        //로그인 안됨
        assertEquals(ErrorMessage.NOT_LOGIN_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> postService.deletePost(0L)
                ).getCode());

        //다른 유저로 게시글 생성
        memberService.resister(loginReq2);
        var createRes1 = postService.createPost(testBoardId, createReq);
        memberService.logout();
        memberService.resister(loginReq);

        //게시글 없음
        assertEquals(ErrorMessage.NOT_EXISTS_POST_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> postService.deletePost(0L)
                ).getCode());
        //소유권 없음
        assertEquals(ErrorMessage.POST_PERMISSION_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> postService.deletePost(createRes1.getId())
                ).getCode());

        var createRes2 = postService.createPost(testBoardId, createReq);
        postService.deletePost(createRes2.getId());
        var post = postRepository.findById(createRes2.getId());
        //then
        assertEquals(board.getPostCount(), postSize + 1);
        assertTrue(post.isEmpty());
    }

    @Test
    void getPostList() {
        //given
        var createReq = PostRequest.builder()
                .title("제목")
                .content("내용")
                .hashTags(new String[]{"태그1", "태그2"})
                .build();

        //게시글 생성
        memberService.resister(loginReq);
        IntStream.range(0, 50).forEach(i -> postService.createPost(testBoardId, createReq));
        memberService.logout();
        //게시판 없음
        assertEquals(ErrorMessage.NOT_EXISTS_BOARD_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> postService.getPostList(0L, 0)
                ).getCode());
        //페이지 오류
        assertEquals(ErrorMessage.PAGE_OUT_OF_RANGE_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> postService.getPostList(testBoardId, 3)
                ).getCode());
        var postResList = postService.getPostList(testBoardId, 0);
        //then
        assertEquals(20, postResList.size());
    }

    @Test
    void getPost() {
        //given
        var createReq = PostRequest.builder()
                .title("제목")
                .content("내용")
                .hashTags(new String[]{"태그1", "태그2"})
                .build();

        //게시글 생성
        memberService.resister(loginReq);
        var createRes = postService.createPost(testBoardId, createReq);
        memberService.logout();

        //게시글 없음
        assertEquals(ErrorMessage.NOT_EXISTS_POST_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> postService.getPost(0L)
                ).getCode());

        var postRes = postService.getPost(createRes.getId());
        //then
        assertEquals(postRes.getTitle(), createRes.getTitle());
        assertEquals(postRes.getContent(), createRes.getContent());
        assertArrayEquals(postRes.getHashtags(), createRes.getHashtags());
    }
}