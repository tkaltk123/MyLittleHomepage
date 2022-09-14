package com.yunseojin.MyLittleHomepage.post.service;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.service.MemberServiceImpl;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static com.yunseojin.MyLittleHomepage.TestUtil.assertError;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class PostServiceImplTest {

    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private PostRepository postRepository;

    private static MemberRequest memberRequest;
    private static PostRequest postRequest;

    private BoardEntity board;
    private PostEntity post;

    @BeforeAll
    static void setup() {

        memberRequest = MemberRequest.builder()
                .loginId("testuser")
                .password("1234")
                .nickname("testuser")
                .build();

        postRequest = PostRequest.builder()
                .title("제목")
                .content("내용")
                .hashTags(new String[]{"태그1", "태그2"})
                .build();
    }

    @BeforeEach
    public void init() {

        board = BoardEntity.builder().
                name("testBoard")
                .build();

        memberService.register(memberRequest);

        board = boardRepository.save(board);
        var postRes = postService.createPost(board.getId(), postRequest);

        post = postRepository.findById(postRes.getId()).get();
    }

    @Test
    void createPost() {

        //로그인 안됨
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                postService.createPost(board.getId(), postRequest)
        );

        //로그인
        memberService.login(memberRequest);

        //게시판 없음
        assertError(ErrorMessage.NOT_EXISTS_BOARD_EXCEPTION, () ->
                postService.createPost(0L, postRequest)
        );

        //게시글 작성
        var postRes = postService.createPost(board.getId(), postRequest);

        //연속 작성
        assertError(ErrorMessage.POST_REPEAT_EXCEPTION, () ->
                postService.createPost(board.getId(), postRequest)
        );

        //then
        assertEquals(2, board.getBoardCount().getPostCount());
        assertEquals(board.getId(), postRes.getBoardId());
        assertEquals(postRequest.getTitle(), postRes.getTitle());
        assertEquals(postRequest.getContent(), postRes.getContent());
    }

    @Test
    void updatePost() {

        //로그인 안됨
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                postService.updatePost(post.getId(), postRequest)
        );

        //로그인
        memberService.login(memberRequest);

        //게시글 없음
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                postService.updatePost(0L, postRequest)
        );

        //게시글 수정
        var updateRequest = PostRequest.builder()
                .title("제목2")
                .content("내용2")
                .build();
        postService.updatePost(post.getId(), updateRequest);

        //다른 유저로 로그인
        var registerRequest = MemberRequest.builder()
                .loginId("testuser2")
                .password("1234")
                .nickname("testuser2")
                .build();
        memberService.register(registerRequest);

        //수정할 게시글의 작성자가 아닌 경우
        assertError(ErrorMessage.NOT_WRITER_EXCEPTION, () ->
                postService.updatePost(post.getId(), updateRequest)
        );

        //then
        assertEquals(updateRequest.getTitle(), post.getTitle());
        assertEquals(updateRequest.getContent(), post.getContent());
    }

    @Test
    void deletePost() {

        //로그인 안됨
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                postService.deletePost(post.getId())
        );

        //로그인
        memberService.login(memberRequest);

        //게시글 없음
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                postService.deletePost(0L)
        );

        //다른 유저로 로그인
        var registerRequest = MemberRequest.builder()
                .loginId("testuser2")
                .password("1234")
                .nickname("testuser2")
                .build();
        memberService.register(registerRequest);

        //삭제할 게시글의 작성자가 아닌 경우
        assertError(ErrorMessage.NOT_WRITER_EXCEPTION, () ->
                postService.deletePost(post.getId())
        );

        //게시글 삭제
        memberService.login(memberRequest);
        postService.deletePost(post.getId());

        //then
        assertEquals(1, post.getIsDeleted());
        assertEquals(0, board.getPostCount());
    }

    @Test
    void getPost() {

        //게시글 없음
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                postService.getPost(0L)
        );

        //게시글 조회
        var postRes = postService.getPost(post.getId());

        //then
        assertEquals(postRes.getTitle(), post.getTitle());
        assertEquals(postRes.getContent(), post.getContent());
        assertEquals(1, postRes.getViewCount());
        postService.getPost(post.getId());
        assertEquals(1, postRes.getViewCount());
    }
}