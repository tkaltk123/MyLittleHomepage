package com.yunseojin.MyLittleHomepage.evaluation.service;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.comment.repository.CommentRepository;
import com.yunseojin.MyLittleHomepage.comment.service.CommentServiceImpl;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EvaluationServiceImplTest {
    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    private EvaluationServiceImpl evaluationService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    private static MemberRequest loginReq;
    private static PostRequest postReq;
    private static final Long testBoardId = 1L;

    @BeforeAll
    public static void setUp() {
        loginReq = MemberRequest.builder()
                .loginId("testuser")
                .password("1234")
                .nickname("testuser")
                .build();
        postReq = PostRequest.builder()
                .title("제목")
                .content("내용")
                .hashTags(new String[]{"태그1", "태그2"})
                .build();
    }

    private PostEntity post;
    private CommentEntity comment;

    @BeforeEach
    public void init() {
        var commentReq = CommentRequest.builder()
                .content("댓글")
                .build();
        memberService.register(loginReq);
        var postRes = postService.createPost(testBoardId, postReq);
        post = postRepository.getPost(postRes.getId());
        var commentRes = commentService.createComment(postRes.getId(), commentReq);
        comment = commentRepository.getComment(commentRes.getId());
        memberService.logout();
    }

    @Test
    void post() {
        var postId = post.getId();
        //로그인 안됨
        assertEquals(ErrorMessage.NOT_LOGIN_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> evaluationService.likePost(post.getId())
                ).getCode());
        memberService.login(loginReq);
        //게시글 없음
        assertEquals(ErrorMessage.NOT_EXISTS_POST_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> evaluationService.likePost(0L)
                ).getCode());
        var likeCount = post.getPostCount().getLikeCount();
        var dislikeCount = post.getPostCount().getDislikeCount();
        assertEquals("좋아요 성공", evaluationService.likePost(postId));
        assertEquals(post.getPostCount().getLikeCount(), likeCount + 1);
        assertEquals("좋아요 취소", evaluationService.likePost(postId));
        assertEquals(post.getPostCount().getLikeCount(), likeCount);
        assertEquals("싫어요 성공", evaluationService.dislikePost(postId));
        assertEquals(post.getPostCount().getDislikeCount(), dislikeCount + 1);
        assertEquals("싫어요 취소", evaluationService.dislikePost(postId));
        assertEquals(post.getPostCount().getDislikeCount(), dislikeCount);
        //싫어요 후 좋아요
        evaluationService.dislikePost(postId);
        evaluationService.likePost(postId);
        assertEquals(post.getPostCount().getLikeCount(), likeCount + 1);
        assertEquals(post.getPostCount().getDislikeCount(), dislikeCount);
        //좋아요 후 싫어요
        evaluationService.dislikePost(postId);
        assertEquals(post.getPostCount().getLikeCount(), likeCount);
        assertEquals(post.getPostCount().getDislikeCount(), dislikeCount + 1);
    }

    @Test
    void comment() {
        var commentId = comment.getId();
        //로그인 안됨
        assertEquals(ErrorMessage.NOT_LOGIN_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> evaluationService.likeComment(comment.getId())
                ).getCode());
        memberService.login(loginReq);
        //게시글 없음
        assertEquals(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> evaluationService.likeComment(0L)
                ).getCode());
        var likeCount = comment.getCommentCount().getLikeCount();
        var dislikeCount = comment.getCommentCount().getDislikeCount();
        assertEquals("좋아요 성공", evaluationService.likeComment(commentId));
        assertEquals(comment.getCommentCount().getLikeCount(), likeCount + 1);
        assertEquals("좋아요 취소", evaluationService.likeComment(commentId));
        assertEquals(comment.getCommentCount().getLikeCount(), likeCount);
        assertEquals("싫어요 성공", evaluationService.dislikeComment(commentId));
        assertEquals(comment.getCommentCount().getDislikeCount(), dislikeCount + 1);
        assertEquals("싫어요 취소", evaluationService.dislikeComment(commentId));
        assertEquals(comment.getCommentCount().getDislikeCount(), dislikeCount);
        //싫어요 후 좋아요
        evaluationService.dislikeComment(commentId);
        evaluationService.likeComment(commentId);
        assertEquals(comment.getCommentCount().getLikeCount(), likeCount + 1);
        assertEquals(comment.getCommentCount().getDislikeCount(), dislikeCount);
        //좋아요 후 싫어요
        evaluationService.dislikeComment(commentId);
        assertEquals(comment.getCommentCount().getLikeCount(), likeCount);
        assertEquals(comment.getCommentCount().getDislikeCount(), dislikeCount + 1);
    }
}