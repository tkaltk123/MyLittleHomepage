package com.yunseojin.MyLittleHomepage.evaluation.service;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.comment.repository.CommentRepository;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import javax.transaction.Transactional;

import static com.yunseojin.MyLittleHomepage.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class EvaluationServiceImplTest {


    private final EvaluationService evaluationService;

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private MemberEntity member;
    private PostEntity post;
    private CommentEntity comment;

    @BeforeEach
    public void init() {

        member = memberRepository.save(createTestMember("testUser", "testUser"));
        BoardEntity board = boardRepository.save(createTestBoard("testBoard"));
        post = postRepository.save(createTestPost(member, board, "내용"));
        comment = commentRepository.save(createTestComment(member, post, "내용"));
    }

    @Test
    void post() {

        //게시글 없음
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                evaluationService.likePost(member.getId(), 0L)
        );
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                evaluationService.dislikePost(member.getId(), 0L)
        );

        //게시글 좋아요
        evaluationService.likePost(member.getId(), post.getId());
        assertEquals(1, post.getLikeCount());

        //좋아요 취소
        evaluationService.likePost(member.getId(), post.getId());
        assertEquals(0, post.getLikeCount());

        //좋아요 후 싫어요
        evaluationService.likePost(member.getId(), post.getId());
        evaluationService.dislikePost(member.getId(), post.getId());
        assertEquals(0, post.getLikeCount());
        assertEquals(1, post.getDislikeCount());

        //싫어요 취소
        evaluationService.dislikePost(member.getId(), post.getId());
        assertEquals(0, post.getDislikeCount());

        //싫어요 후 좋아요
        evaluationService.dislikePost(member.getId(), post.getId());
        evaluationService.likePost(member.getId(), post.getId());
        assertEquals(0, post.getDislikeCount());
        assertEquals(1, post.getLikeCount());
    }

    @Test
    void comment() {

        //댓글 없음
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                evaluationService.likeComment(member.getId(), 0L)
        );
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                evaluationService.likeComment(member.getId(), 0L)
        );

        //댓글 좋아요
        evaluationService.likeComment(member.getId(), comment.getId());
        assertEquals(1, comment.getLikeCount());

        //좋아요 취소
        evaluationService.likeComment(member.getId(), comment.getId());
        assertEquals(0, comment.getLikeCount());

        //좋아요 후 싫어요
        evaluationService.likeComment(member.getId(), comment.getId());
        evaluationService.dislikeComment(member.getId(), comment.getId());
        assertEquals(0, comment.getLikeCount());
        assertEquals(1, comment.getDislikeCount());

        //싫어요 취소
        evaluationService.dislikeComment(member.getId(), comment.getId());
        assertEquals(0, comment.getDislikeCount());

        //싫어요 후 좋아요
        evaluationService.dislikeComment(member.getId(), comment.getId());
        evaluationService.likeComment(member.getId(), comment.getId());
        assertEquals(0, comment.getDislikeCount());
        assertEquals(1, comment.getLikeCount());
    }
}