package com.yunseojin.MyLittleHomepage.comment.service;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.comment.mapper.CommentMapper;
import com.yunseojin.MyLittleHomepage.comment.repository.CommentRepository;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.member.service.MemberServiceImpl;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import com.yunseojin.MyLittleHomepage.post.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestConstructor;

import javax.transaction.Transactional;

import java.awt.print.Pageable;

import static com.yunseojin.MyLittleHomepage.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CommentServiceImplTest {


    private final CommentServiceImpl commentService;

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private MemberEntity member;
    private PostEntity post;
    private CommentEntity comment;

    @BeforeEach
    public void init() {

        member = memberRepository.save(createTestMember("testId"));
        BoardEntity board = boardRepository.save(createTestBoard("testBoard"));
        post = postRepository.save(createTestPost(member, board, "내용"));
        comment = commentRepository.save(createTestComment(member, post, "내용"));
        post.getPostCount().increaseCommentCount();
    }

    @Test
    void createComment() {

        var commentRequest = createCommentRequest(null, null, "내용");

        //게시글 없음
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                commentService.createComment(member.getId(), 0L, commentRequest)
        );

        //댓글 작성
        var commentResponse = commentService.createComment(member.getId(), post.getId(), commentRequest);
        var comment = commentRepository.findById(commentResponse.getId()).get();

        //댓글 연속 작성 시
        assertError(ErrorMessage.COMMENT_REPEAT_EXCEPTION, () ->
                commentService.createComment(member.getId(), post.getId(), commentRequest)
        );

        //멤버2 생성
        var member2 = memberRepository.save(createTestMember("testUser2"));

        //존재하지 않는 댓글에 대댓글 작성 시
        var errorRequest = commentRequest.toBuilder().parentId(0L).build();
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                commentService.createComment(member2.getId(), post.getId(), errorRequest)
        );

        //대댓글 작성
        var replyRequest = commentRequest.toBuilder().parentId(commentResponse.getId()).build();
        var replyResponse = commentService.createComment(member2.getId(), post.getId(), replyRequest);
        var reply = commentRepository.findById(replyResponse.getId()).get();

        //then
        assertEquals(commentResponse.getPostId(), post.getId());
        assertEquals(commentResponse.getWriterName(), member.getNickname());
        assertEquals(post.getPostCount().getCommentCount(), 3);
        assertEquals(comment.getChildren().get(0), reply);
    }

    @Test
    void updateComment() {

        var errorRequest = createCommentRequest(null, 0L, "내용2");
        var updateRequest = createCommentRequest(null, comment.getId(), "내용2");

        //댓글 없음
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                commentService.updateComment(member.getId(), errorRequest)
        );

        //댓글 수정
        var updateRes = commentService.updateComment(member.getId(), updateRequest);

        //멤버2 생성
        var member2 = memberRepository.save(createTestMember("testUser2"));

        //사용자가 작성하지 않은 댓글을 수정할 경우
        assertError(ErrorMessage.NOT_WRITER_EXCEPTION, () ->
                commentService.updateComment(member2.getId(), updateRequest)
        );

        //then
        assertEquals(comment.getContent(), updateRes.getContent());
    }

    @Test
    void deleteComment() {

        //댓글 없음
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                commentService.deleteComment(member.getId(), 0L)
        );

        //멤버2 생성
        var member2 = memberRepository.save(createTestMember("testUser2"));

        //사용자가 작성하지 않은 댓글을 삭제할 경우
        assertError(ErrorMessage.NOT_WRITER_EXCEPTION, () ->
                commentService.deleteComment(member2.getId(), comment.getId())
        );

        //댓글 삭제
        commentService.deleteComment(member.getId(), comment.getId());

        //then
        assertEquals(comment.getIsDeleted(), 1);
        assertEquals(post.getCommentCount(), 0);
    }

    @Test
    void getComment() {

        //댓글 없음
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                commentService.getComment(0L)
        );

        //댓글 검색
        var commentResponse = commentService.getComment(comment.getId());

        //then
        assertEquals(comment.getId(), commentResponse.getId());
    }

    @Test
    void getCommentList() {

        for (int i = 0; i < 20; ++i) {

            var comment = createTestComment(member, post, "댓글");

            post.increaseCommentCount();
            commentRepository.save(comment);
        }

        var reply = createTestComment(member, post, "댓글");
        reply.setParent(comment);
        post.increaseCommentCount();
        commentRepository.save(reply);

        //게시글 없음
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                commentService.getCommentList(0L, 0)
        );

        //댓글 검색
        var comments0 = commentService.getCommentList(post.getId(), 0).getContent();
        var comments1 = commentService.getCommentList(post.getId(), 1).getContent();
        var comments2 = commentService.getCommentList(post.getId(), 2).getContent();

        //then
        assertEquals(20, comments0.size());
        assertEquals(1, comments1.size());
        assertEquals(1, comments2.size());
        assertEquals(comments1.get(0).getId(), comments2.get(0).getId());
        assertEquals(1, comments0.get(0).getChildren().length);
        assertEquals(0, comments0.get(1).getChildren().length);
        assertError(ErrorMessage.PAGE_OUT_OF_RANGE_EXCEPTION, () ->
                commentService.getCommentList(post.getId(), -1)
        );
    }
}