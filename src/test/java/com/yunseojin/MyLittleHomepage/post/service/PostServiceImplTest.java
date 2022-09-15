package com.yunseojin.MyLittleHomepage.post.service;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
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
class PostServiceImplTest {

    private final PostServiceImpl postService;

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    private MemberEntity member;
    private MemberEntity member2;
    private BoardEntity board;
    private PostEntity post;

    @BeforeEach
    public void init() {

        member = memberRepository.save(createTestMember("testUser", "testUser"));
        member2 = memberRepository.save(createTestMember("testUser2", "testUser2"));
        board = boardRepository.save(createTestBoard("testBoard"));
        post = postRepository.save(createTestPost(member, board, "내용"));
        board.increasePostCount();
    }

    @Test
    void createPost() {

        var postRequest = createPostRequest("내용");

        //게시판 없음
        assertError(ErrorMessage.NOT_EXISTS_BOARD_EXCEPTION, () ->
                postService.createPost(member.getId(), 0L, postRequest)
        );

        //게시글 작성
        var postRes = postService.createPost(member.getId(), board.getId(), postRequest);

        //연속 작성
        assertError(ErrorMessage.POST_REPEAT_EXCEPTION, () ->
                postService.createPost(member.getId(), board.getId(), postRequest)
        );

        //then
        assertEquals(2, board.getBoardCount().getPostCount());
        assertEquals(board.getId(), postRes.getBoardId());
        assertEquals(postRequest.getTitle(), postRes.getTitle());
        assertEquals(postRequest.getContent(), postRes.getContent());
    }

    @Test
    void updatePost() {

        var updateRequest = createPostRequest("내용2");

        //게시글 없음
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                postService.updatePost(member.getId(), 0L, updateRequest)
        );

        //게시글 수정
        postService.updatePost(member.getId(), post.getId(), updateRequest);

        //수정할 게시글의 작성자가 아닌 경우
        assertError(ErrorMessage.NOT_WRITER_EXCEPTION, () ->
                postService.updatePost(member2.getId(), post.getId(), updateRequest)
        );

        //then
        assertEquals(updateRequest.getTitle(), post.getTitle());
        assertEquals(updateRequest.getContent(), post.getContent());
    }

    @Test
    void deletePost() {

        //게시글 없음
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                postService.deletePost(member.getId(), 0L)
        );

        //삭제할 게시글의 작성자가 아닌 경우
        assertError(ErrorMessage.NOT_WRITER_EXCEPTION, () ->
                postService.deletePost(member2.getId(), post.getId())
        );

        //게시글 삭제
        postService.deletePost(member.getId(), post.getId());

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
        postService.getPost(post.getId());
    }
}