package com.yunseojin.MyLittleHomepage.comment.service;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.repository.CommentRepository;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.service.MemberServiceImpl;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import com.yunseojin.MyLittleHomepage.post.service.PostServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    private static final Long testBoardId = 1L;

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
    }

    @Test
    void createComment() {
        //given
        var req1 = CommentRequest.builder()
                .content("댓글")
                .build();
        var req2 = CommentRequest.builder()
                .content("댓글")
                .parentId(0L)
                .build();
        //로그인 안됨
        assertEquals(ErrorMessage.NOT_LOGIN_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> commentService.createComment(0L, req1)
                ).getCode());
        //회원가입&로그인
        memberService.resister(loginReq);
        //게시글 없음
        assertEquals(ErrorMessage.NOT_EXISTS_POST_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> commentService.createComment(0L, req1)
                ).getCode());
        //게시글 생성
        var postRes = postService.createPost(testBoardId, postReq);
        //댓글 없음
        assertEquals(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> commentService.createComment(postRes.getId(), req2)
                ).getCode());
        //댓글 생성
        var commentRes = commentService.createComment(postRes.getId(), req1);
        //then
        assertEquals(commentRes.getPostId(), postRes.getId());
        assertEquals(commentRes.getWriterName(), loginReq.getNickname());
        assertEquals(commentRes.getContent(), req1.getContent());
        //대댓글
        var req3 = CommentRequest.builder()
                .content("댓글")
                .parentId(commentRes.getId())
                .build();
        var childRes = commentService.createComment(postRes.getId(), req3);
        var comment = commentRepository.getComment(commentRes.getId());
        var child = comment.getChildren().get(0);
        assertEquals(child.getId(), childRes.getId());
        assertEquals(child.getParent().getId(), commentRes.getId());
        //게시글의 댓글수 검증
        var post = postRepository.getPost(postRes.getId());
        assertEquals(post.getCommentCount(), postRes.getCommentCount() + 2);
    }

    @Test
    void updateComment() {
        //given
        var createReq = CommentRequest.builder()
                .content("댓글")
                .build();
        var updateReq = CommentRequest.builder()
                .content("댓글2")
                .build();
        //로그인 안됨
        assertEquals(ErrorMessage.NOT_LOGIN_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> commentService.updateComment(0L, updateReq)
                ).getCode());
        memberService.resister(loginReq);
        var postRes = postService.createPost(testBoardId, postReq);
        //댓글 없음
        assertEquals(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> commentService.updateComment(0L, updateReq)
                ).getCode());
        var commentRes = commentService.createComment(postRes.getId(), createReq);
        var updateRes = commentService.updateComment(commentRes.getId(), updateReq);
        //then
        assertEquals(updateRes.getPostId(), postRes.getId());
        assertEquals(updateRes.getWriterName(), loginReq.getNickname());
        assertEquals(updateRes.getContent(), updateReq.getContent());
    }

    @Test
    void deleteComment() {
        //given
        var createReq = CommentRequest.builder()
                .content("댓글")
                .build();
        //로그인 안됨
        assertEquals(ErrorMessage.NOT_LOGIN_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> commentService.deleteComment(0L)
                ).getCode());
        memberService.resister(loginReq);
        var postRes = postService.createPost(testBoardId, postReq);
        //댓글 없음
        assertEquals(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> commentService.deleteComment(0L)
                ).getCode());
        var commentRes1 = commentService.createComment(postRes.getId(), createReq);
        memberService.logout();
        memberService.resister(loginReq2);
        //작성자가 아님
        assertEquals(ErrorMessage.NOT_WRITER_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> commentService.deleteComment(commentRes1.getId())
                ).getCode());
        var commentRes2 = commentService.createComment(postRes.getId(), createReq);
        commentService.deleteComment(commentRes2.getId());
        //댓글 없음
        assertEquals(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> commentRepository.getComment(commentRes2.getId())
                ).getCode());
        var post = postRepository.getPost(postRes.getId());
        assertEquals(post.getCommentCount(), postRes.getCommentCount() + 1);
    }

    @Test
    void getCommentList() {
    }
}