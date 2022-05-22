package com.yunseojin.MyLittleHomepage.comment.service;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.repository.CommentRepository;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.service.MemberServiceImpl;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import com.yunseojin.MyLittleHomepage.post.service.PostServiceImpl;
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
class CommentServiceImplTest {
    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    private static MemberRequest loginReq;
    private static MemberRequest loginReq2;
    private static PostRequest postReq;
    private static CommentRequest commentReq;
    private static final Long testBoardId = 1L;
    private PostEntity post;

    @BeforeAll
    public static void setUp() {
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
        postReq = PostRequest.builder()
                .title("제목")
                .content("내용")
                .hashTags(new String[]{"태그1", "태그2"})
                .build();
        commentReq = CommentRequest.builder()
                .content("댓글")
                .build();
    }

    @BeforeEach
    public void init() {
        memberService.resister(loginReq);
        var postRes = postService.createPost(testBoardId, postReq);
        post = postRepository.getPost(postRes.getId());
        memberService.logout();

        memberService.resister(loginReq2);
        memberService.logout();
    }

    @Test
    void createComment() {
        //given
        var commentReq2 = CommentRequest.builder()
                .content("댓글")
                .parentId(0L)
                .build();
        //로그인 안됨
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                commentService.createComment(0L, commentReq)
        );
        //로그인
        memberService.login(loginReq);
        //게시글 없음
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                commentService.createComment(0L, commentReq)
        );
        //댓글 없음
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                commentService.createComment(post.getId(), commentReq2)
        );
        //댓글 생성
        var commentCount = post.getCommentCount();
        var commentRes = commentService.createComment(post.getId(), commentReq);
        //댓글 연속 생성
        assertError(ErrorMessage.COMMENT_REPEAT_EXCEPTION, () ->
                commentService.createComment(post.getId(), commentReq)
        );
        //then
        assertEquals(commentRes.getPostId(), post.getId());
        assertEquals(commentRes.getWriterName(), loginReq.getNickname());
        assertEquals(commentRes.getContent(), commentReq.getContent());
        assertEquals(post.getCommentCount(), commentCount + 1);
        //대댓글
        memberService.logout();
        memberService.login(loginReq);
        var req3 = CommentRequest.builder()
                .content("댓글")
                .parentId(commentRes.getId())
                .build();
        var childRes = commentService.createComment(post.getId(), req3);
        var comment = commentRepository.getComment(commentRes.getId());
        var child = comment.getChildren().get(0);
        assertEquals(child.getId(), childRes.getId());
        assertEquals(child.getParent().getId(), commentRes.getId());
    }

    @Test
    void updateComment() {
        //given
        var updateReq = CommentRequest.builder()
                .content("댓글2")
                .build();
        //로그인 안됨
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                commentService.updateComment(0L, updateReq)
        );
        //멤버1로 로그인
        memberService.login(loginReq);
        //댓글 없음
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                commentService.updateComment(0L, updateReq)
        );
        //댓글 생성
        var commentRes = commentService.createComment(post.getId(), commentReq);
        //멤버2로 로그인
        memberService.logout();
        memberService.login(loginReq2);
        //작성자가 아님
        assertError(ErrorMessage.NOT_WRITER_EXCEPTION, () ->
                commentService.updateComment(commentRes.getId(), updateReq)
        );
        //멤버1로 로그인
        memberService.logout();
        memberService.login(loginReq);
        //댓글 수정
        var updateRes = commentService.updateComment(commentRes.getId(), updateReq);
        //검증
        assertEquals(updateRes.getPostId(), post.getId());
        assertEquals(updateRes.getWriterName(), loginReq.getNickname());
        assertEquals(updateRes.getContent(), updateReq.getContent());
    }

    @Test
    void deleteComment() {
        //로그인 안됨
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                commentService.deleteComment(0L)
        );
        //멤버1로 로그인
        memberService.login(loginReq);
        //댓글 없음
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                commentService.deleteComment(0L)
        );
        //댓글 생성
        var commentRes = commentService.createComment(post.getId(), commentReq);
        var commentCount = post.getCommentCount();
        //멤버2로 로그인
        memberService.logout();
        memberService.login(loginReq2);
        //작성자가 아님
        assertError(ErrorMessage.NOT_WRITER_EXCEPTION, () ->
                commentService.deleteComment(commentRes.getId())
        );
        //멤버1로 로그인
        memberService.logout();
        memberService.login(loginReq);
        //댓글 삭제
        commentService.deleteComment(commentRes.getId());
        //댓글 없음
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                commentRepository.getComment(commentRes.getId())
        );
        assertEquals(post.getCommentCount(), commentCount - 1);
    }

    @Test
    void getCommentList() {
        //게시글 없음
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                commentService.getCommentList(0L, 0)
        );
        //멤버1로 로그인
        memberService.login(loginReq);
        //댓글 작성
        var commentRes = commentService.createComment(post.getId(), commentReq);
        //멤버2로 로그인
        memberService.logout();
        memberService.login(loginReq);
        //대댓글 작성
        var createReq2 = CommentRequest.builder()
                .parentId(commentRes.getId())
                .content("대댓글")
                .build();
        commentService.createComment(post.getId(), createReq2);
        //검증
        var comments = commentService.getCommentList(post.getId(), 0);
        var child = comments.get(0).getChildren()[0];
        assertEquals(comments.size(), 1);
        assertEquals(comments.get(0).getChildren().length, 1);
        assertEquals(child.getContent(), createReq2.getContent());
    }
}