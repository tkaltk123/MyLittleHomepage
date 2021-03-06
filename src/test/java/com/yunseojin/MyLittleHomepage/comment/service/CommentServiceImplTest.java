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
                .title("??????")
                .content("??????")
                .hashTags(new String[]{"??????1", "??????2"})
                .build();
        commentReq = CommentRequest.builder()
                .content("??????")
                .build();
    }

    @BeforeEach
    public void init() {
        memberService.register(loginReq);
        var postRes = postService.createPost(testBoardId, postReq);
        post = postRepository.getPost(postRes.getId());
        memberService.logout();

        memberService.register(loginReq2);
        memberService.logout();
    }

    @Test
    void createComment() {
        //given
        var commentReq2 = CommentRequest.builder()
                .content("??????")
                .parentId(0L)
                .build();
        //????????? ??????
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                commentService.createComment(0L, commentReq)
        );
        //?????????
        memberService.login(loginReq);
        //????????? ??????
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                commentService.createComment(0L, commentReq)
        );
        //?????? ??????
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                commentService.createComment(post.getId(), commentReq2)
        );
        //?????? ??????
        var commentCount = post.getPostCount().getCommentCount();
        var commentRes = commentService.createComment(post.getId(), commentReq);
        //?????? ?????? ??????
        assertError(ErrorMessage.COMMENT_REPEAT_EXCEPTION, () ->
                commentService.createComment(post.getId(), commentReq)
        );
        //then
        assertEquals(commentRes.getPostId(), post.getId());
        assertEquals(commentRes.getWriterName(), loginReq.getNickname());
        assertEquals(commentRes.getContent(), commentReq.getContent());
        assertEquals(post.getPostCount().getCommentCount(), commentCount + 1);
        //?????????
        memberService.logout();
        memberService.login(loginReq);
        var req3 = CommentRequest.builder()
                .content("??????")
                .parentId(commentRes.getId())
                .build();
        var childRes = commentService.createComment(post.getId(), req3);
        var comment = commentRepository.getComment(commentRes.getId());
        var child = comment.getChildren().get(0);
        assertEquals(child.getId(), childRes.getId());
        assertEquals(child.getParent().getId(), commentRes.getId());
    }

//    @Test
//    void updateComment() {
//        //given
//        var updateReq = CommentRequest.builder()
//                .content("??????2")
//                .build();
//        //????????? ??????
//        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
//                commentService.updateComment(updateReq)
//        );
//        //??????1??? ?????????
//        memberService.login(loginReq);
//        //?????? ??????
//        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
//                commentService.updateComment(0L,updateReq)
//        );
//        //?????? ??????
//        var commentRes = commentService.createComment(post.getId(), commentReq);
//        //??????2??? ?????????
//        memberService.logout();
//        memberService.login(loginReq2);
//        //???????????? ??????
//        assertError(ErrorMessage.NOT_WRITER_EXCEPTION, () ->
//                commentService.updateComment(commentRes.getId(), updateReq)
//        );
//        //??????1??? ?????????
//        memberService.logout();
//        memberService.login(loginReq);
//        //?????? ??????
//        var updateRes = commentService.updateComment(commentRes.getId(), updateReq);
//        //??????
//        assertEquals(updateRes.getPostId(), post.getId());
//        assertEquals(updateRes.getWriterName(), loginReq.getNickname());
//        assertEquals(updateRes.getContent(), updateReq.getContent());
//    }

    @Test
    void deleteComment() {
        //????????? ??????
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                commentService.deleteComment(0L)
        );
        //??????1??? ?????????
        memberService.login(loginReq);
        //?????? ??????
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                commentService.deleteComment(0L)
        );
        //?????? ??????
        var commentRes = commentService.createComment(post.getId(), commentReq);
        var commentCount = post.getPostCount().getCommentCount();
        //??????2??? ?????????
        memberService.logout();
        memberService.login(loginReq2);
        //???????????? ??????
        assertError(ErrorMessage.NOT_WRITER_EXCEPTION, () ->
                commentService.deleteComment(commentRes.getId())
        );
        //??????1??? ?????????
        memberService.logout();
        memberService.login(loginReq);
        //?????? ??????
        commentService.deleteComment(commentRes.getId());
        //?????? ??????
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                commentRepository.getComment(commentRes.getId())
        );
        assertEquals(post.getPostCount().getCommentCount(), commentCount - 1);
    }

//    @Test
//    void getCommentList() {
//        //????????? ??????
//        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
//                commentService.getCommentList(0L, 0)
//        );
//        //??????1??? ?????????
//        memberService.login(loginReq);
//        //?????? ??????
//        var commentRes = commentService.createComment(post.getId(), commentReq);
//        //??????2??? ?????????
//        memberService.logout();
//        memberService.login(loginReq);
//        //????????? ??????
//        var createReq2 = CommentRequest.builder()
//                .parentId(commentRes.getId())
//                .content("?????????")
//                .build();
//        commentService.createComment(post.getId(), createReq2);
//        //??????
//        var comments = commentService.getCommentList(post.getId(), 0);
//        var child = comments.get(0).getChildren()[0];
//        assertEquals(comments.size(), 1);
//        assertEquals(comments.get(0).getChildren().length, 1);
//        assertEquals(child.getContent(), createReq2.getContent());
//    }
}