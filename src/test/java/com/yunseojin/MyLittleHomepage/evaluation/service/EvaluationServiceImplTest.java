package com.yunseojin.MyLittleHomepage.evaluation.service;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.comment.repository.CommentRepository;
import com.yunseojin.MyLittleHomepage.comment.service.CommentServiceImpl;
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
    private BoardRepository boardRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    private static MemberRequest memberRequest;
    private static PostRequest postRequest;
    private static CommentRequest commentRequest;

    private PostEntity post;
    private CommentEntity comment;

    @BeforeAll
    public static void setUp() {

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

        commentRequest = CommentRequest.builder()
                .content("댓글")
                .build();
    }

    @BeforeEach
    public void init() {

        var board = BoardEntity.builder().
                name("testBoard")
                .build();

        memberService.register(memberRequest);

        board = boardRepository.save(board);
        var postRes = postService.createPost(board.getId(), postRequest);
        var commentRes = commentService.createComment(postRes.getId(), commentRequest);

        post = postRepository.findById(postRes.getId()).get();
        comment = commentRepository.findById(commentRes.getId()).get();
    }

    @Test
    void post() {
        
        //로그인 안됨
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                evaluationService.likePost(post.getId())
        );
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                evaluationService.dislikePost(post.getId())
        );
        
        //로그인
        memberService.login(memberRequest);
        
        //게시글 없음
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                evaluationService.likePost(0L)
        );
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                evaluationService.dislikePost(0L)
        );
        
        //게시글 좋아요
        evaluationService.likePost(post.getId());
        assertEquals(1,post.getLikeCount());
        
        //좋아요 취소
        evaluationService.likePost(post.getId());
        assertEquals(0,post.getLikeCount());
        
        //좋아요 후 싫어요
        evaluationService.likePost(post.getId());
        evaluationService.dislikePost(post.getId());
        assertEquals(0,post.getLikeCount());
        assertEquals(1,post.getDislikeCount());
        
        //싫어요 취소
        evaluationService.dislikePost(post.getId());
        assertEquals(0,post.getDislikeCount());
        
        //싫어요 후 좋아요
        evaluationService.dislikePost(post.getId());
        evaluationService.likePost(post.getId());
        assertEquals(0,post.getDislikeCount());
        assertEquals(1,post.getLikeCount());
    }

    @Test
    void comment() {
        
        //로그인 안됨
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                evaluationService.likeComment(comment.getId())
        );
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                evaluationService.dislikeComment(comment.getId())
        );

        //로그인
        memberService.login(memberRequest);

        //댓글 없음
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                evaluationService.likeComment(0L)
        );
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                evaluationService.likeComment(0L)
        );

        //댓글 좋아요
        evaluationService.likeComment(comment.getId());
        assertEquals(1,comment.getLikeCount());

        //좋아요 취소
        evaluationService.likeComment(comment.getId());
        assertEquals(0,comment.getLikeCount());

        //좋아요 후 싫어요
        evaluationService.likeComment(comment.getId());
        evaluationService.dislikeComment(comment.getId());
        assertEquals(0,comment.getLikeCount());
        assertEquals(1,comment.getDislikeCount());

        //싫어요 취소
        evaluationService.dislikeComment(comment.getId());
        assertEquals(0,comment.getDislikeCount());

        //싫어요 후 좋아요
        evaluationService.dislikeComment(comment.getId());
        evaluationService.likeComment(comment.getId());
        assertEquals(0,comment.getDislikeCount());
        assertEquals(1,comment.getLikeCount());
    }
}