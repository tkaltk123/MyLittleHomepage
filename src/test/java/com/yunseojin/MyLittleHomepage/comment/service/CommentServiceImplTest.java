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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;

import java.awt.print.Pageable;

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
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    private static MemberRequest memberRequest;
    private static PostRequest postRequest;
    private static CommentRequest commentRequest;

    private PostEntity post;

    @BeforeAll
    public static void setup() {

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
        //var postRes = postService.createPost(board.getId(), postRequest);

        //post = postRepository.findById(postRes.getId()).get();
    }

    @Test
    void createComment() {

        //로그인 안됨
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                commentService.createComment(post.getId(), commentRequest)
        );

        //로그인
        memberService.login(memberRequest);

        //게시글 없음
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                commentService.createComment(0L, commentRequest)
        );

        //댓글 작성
        var commentResponse = commentService.createComment(post.getId(), commentRequest);
        var comment = commentRepository.findById(commentResponse.getId()).get();

        //댓글 연속 작성 시
        assertError(ErrorMessage.COMMENT_REPEAT_EXCEPTION, () ->
                commentService.createComment(post.getId(), commentRequest)
        );

        //재로그인
        memberService.login(memberRequest);

        //존재하지 않는 댓글에 대댓글 작성 시
        var errorRequest = commentRequest.toBuilder().parentId(0L).build();
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                commentService.createComment(post.getId(), errorRequest)
        );

        //대댓글 작성
        var replyRequest = commentRequest.toBuilder().parentId(commentResponse.getId()).build();
        var replyResponse = commentService.createComment(post.getId(), replyRequest);
        var reply = commentRepository.findById(replyResponse.getId()).get();

        //then
        assertEquals(commentResponse.getPostId(), post.getId());
        assertEquals(commentResponse.getWriterName(), memberRequest.getNickname());
        assertEquals(post.getPostCount().getCommentCount(), 2);
        assertEquals(comment.getChildren().get(0), reply);
    }

    @Test
    void updateComment() {

        var errorRequest = commentRequest.toBuilder().commentId(0L).build();

        //로그인 안됨
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                commentService.updateComment(errorRequest)
        );

        //로그인
        memberService.login(memberRequest);

        //댓글 없음
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                commentService.updateComment(errorRequest)
        );

        //댓글 작성
        var commentResponse = commentService.createComment(post.getId(), commentRequest);
        var comment = commentRepository.findById(commentResponse.getId()).get();

        //댓글 수정
        var updateRequest = CommentRequest.builder()
                .content("댓글2")
                .commentId(comment.getId())
                .build();
        var updateRes = commentService.updateComment(updateRequest);

        //멤버2 생성
        var member2Request = memberRequest.toBuilder()
                .loginId("testuser2")
                .nickname("testuser2")
                .build();
        memberService.register(member2Request);

        //사용자가 작성하지 않은 댓글을 수정할 경우
        assertError(ErrorMessage.NOT_WRITER_EXCEPTION, () ->
                commentService.updateComment(updateRequest)
        );

        //then
        assertEquals(comment.getContent(), updateRes.getContent());
    }

    @Test
    void deleteComment() {

        //로그인 안됨
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                commentService.deleteComment(0L)
        );

        //로그인
        memberService.login(memberRequest);

        //댓글 없음
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                commentService.deleteComment(0L)
        );

        //댓글 작성
        var commentResponse = commentService.createComment(post.getId(), commentRequest);
        var comment = commentRepository.findById(commentResponse.getId()).get();

        //멤버2 생성
        var member2Request = memberRequest.toBuilder()
                .loginId("testuser2")
                .nickname("testuser2")
                .build();
        memberService.register(member2Request);

        //사용자가 작성하지 않은 댓글을 삭제할 경우
        assertError(ErrorMessage.NOT_WRITER_EXCEPTION, () ->
                commentService.deleteComment(comment.getId())
        );

        //멤버1로 로그인
        memberService.login(memberRequest);

        //댓글 삭제
        commentService.deleteComment(comment.getId());

        //then
        assertEquals(comment.getIsDeleted(), 1);
        assertEquals(post.getCommentCount(), 0);
    }

    @Test
    void getComment() {

        //로그인
        memberService.login(memberRequest);

        //댓글 없음
        assertError(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION, () ->
                commentService.getComment(0L)
        );

        //댓글 작성
        var commentResponse = commentService.createComment(post.getId(), commentRequest);
        var comment = commentRepository.findById(commentResponse.getId()).get();

        //댓글 검색
        commentResponse = commentService.getComment(comment.getId());

        //then
        assertEquals(comment.getId(), commentResponse.getId());
    }

    @Test
    void getCommentList() {

        //댓글 생성
        var writer = memberRepository.findByLoginId(memberRequest.getLoginId()).get();
        var firstComment = getCommentEntity(writer);

        post.increaseCommentCount();
        commentRepository.save(firstComment);

        for (int i = 0; i < 20; ++i) {

            var comment = getCommentEntity(writer);

            post.increaseCommentCount();
            commentRepository.save(comment);
        }

        var reply = getCommentEntity(writer);
        reply.setParent(firstComment);
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

    private CommentEntity getCommentEntity(MemberEntity writer) {
        return CommentMapper.INSTANCE.toCommentEntity(commentRequest)
                .toBuilder()
                .writer(writer)
                .writerName(writer.getNickname())
                .post(post)
                .build();
    }
}