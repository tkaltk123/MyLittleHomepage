package com.yunseojin.MyLittleHomepage.post.service;

import com.yunseojin.MyLittleHomepage.board.entity.BoardCount;
import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.member.service.MemberServiceImpl;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.entity.PostCount;
import com.yunseojin.MyLittleHomepage.post.mapper.PostMapper;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static com.yunseojin.MyLittleHomepage.TestUtil.assertError;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class PostServiceImplTest {
    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BoardRepository boardRepository;

    private static MemberRequest loginReq;
    private static MemberRequest loginReq2;
    private static PostRequest createReq;
    private Long testBoardId;
    private BoardEntity board;
    private MemberEntity member1;

    @BeforeAll
    static void setup() {
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

        createReq = PostRequest.builder()
                .title("??????")
                .content("??????")
                .hashTags(new String[]{"??????1", "??????2"})
                .build();
    }

    @BeforeEach
    public void init() {
        memberService.register(loginReq);
        memberService.logout();
        memberService.register(loginReq2);
        memberService.logout();
        board = BoardEntity.builder().name("test").build();
        boardRepository.save(board);
        testBoardId = board.getId();
        member1 = memberRepository.findByLoginId(loginReq.getLoginId());
    }

    @Test
    void createPost() {
        var postSize = board.getBoardCount().getPostCount();

        //????????? ??????
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                postService.createPost(testBoardId, createReq)
        );
        memberService.login(loginReq);
        //????????? ??????
        assertError(ErrorMessage.NOT_EXISTS_BOARD_EXCEPTION, () ->
                postService.createPost(0L, createReq)
        );
        var postRes = postService.createPost(testBoardId, createReq);
        //?????? ??????
        assertError(ErrorMessage.POST_REPEAT_EXCEPTION, () ->
                postService.createPost(testBoardId, createReq)
        );
        //then
        assertEquals(board.getBoardCount().getPostCount(), postSize + 1);
        assertEquals(postRes.getBoardId(), testBoardId);
        assertEquals(postRes.getTitle(), createReq.getTitle());
        assertEquals(postRes.getContent(), createReq.getContent());
        assertArrayEquals(postRes.getHashtags(), createReq.getHashTags());
    }

    @Test
    void updatePost() {
        //given
        var updateReq = PostRequest.builder()
                .title("??????2")
                .content("??????2")
                .hashTags(new String[]{"??????2"})
                .build();

        //????????? ??????
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                postService.updatePost(0L, createReq)
        );

        //?????? ????????? ????????? ??????
        memberService.login(loginReq2);
        var createRes1 = postService.createPost(testBoardId, createReq);
        memberService.logout();
        memberService.login(loginReq);

        //????????? ??????
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                postService.updatePost(0L, createReq)
        );
        //????????? ??????
        assertError(ErrorMessage.NOT_WRITER_EXCEPTION, () ->
                postService.updatePost(createRes1.getId(), updateReq)
        );

        var createRes2 = postService.createPost(testBoardId, createReq);
        var updateRes = postService.updatePost(createRes2.getId(), updateReq);
        //then
        assertEquals(updateRes.getBoardId(), testBoardId);
        assertEquals(updateRes.getTitle(), updateReq.getTitle());
        assertEquals(updateRes.getContent(), updateReq.getContent());
        assertArrayEquals(updateRes.getHashtags(), updateReq.getHashTags());

    }

    @Test
    void deletePost() {
        //????????? ??????
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                postService.deletePost(0L)
        );
        //????????? ??????
        memberService.login(loginReq);
        var createRes = postService.createPost(testBoardId, createReq);
        var postSize = board.getBoardCount().getPostCount();
        //??????2??? ?????????
        memberService.logout();
        memberService.login(loginReq2);
        //????????? ??????
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                postService.deletePost(0L)
        );
        //????????? ??????
        assertError(ErrorMessage.NOT_WRITER_EXCEPTION, () ->
                postService.deletePost(createRes.getId())
        );
        //??????1??? ?????????
        memberService.logout();
        memberService.login(loginReq);
        //????????? ??????
        postService.deletePost(createRes.getId());
        //????????? ????????? ??????
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                postRepository.getPost(createRes.getId())
        );
        //then
        assertEquals(board.getBoardCount().getPostCount(), postSize - 1);
    }

//    @Test
//    void getPostList() {
//        //????????? ??????
//        for (int i = 0; i < 50; i++) {
//            var post = PostMapper.INSTANCE.toPostEntity(createReq);
//            post.setWriter(member1);
//            post.setWriterName(member1.getNickname());
//            post.setBoard(board);
//            post.setHashtags(createReq.getHashTags());
//            post.setPostCount(new PostCount());
//            postRepository.save(post);
//        }
//        //????????? ??????
//        memberService.login(loginReq);
//        assertError(ErrorMessage.NOT_EXISTS_BOARD_EXCEPTION, () ->
//                postService.getPostList(0L, 0)
//        );
//        //????????? ??????
//        assertError(ErrorMessage.PAGE_OUT_OF_RANGE_EXCEPTION, () ->
//                postService.getPostList(testBoardId, 3)
//        );
//        var postResList = postService.getPostList(testBoardId, 0);
//        //then
//        assertEquals(20, postResList.size());
//    }

    @Test
    void getPost() {
        //????????? ??????
        memberService.login(loginReq);
        var createRes = postService.createPost(testBoardId, createReq);
        memberService.logout();

        //????????? ??????
        assertError(ErrorMessage.NOT_EXISTS_POST_EXCEPTION, () ->
                postService.getPost(0L)
        );
        var post = postRepository.getPost(createRes.getId());
        var view = post.getPostCount().getViewCount();
        //????????? ??????
        var postRes = postService.getPost(createRes.getId());
        //??????
        assertEquals(postRes.getTitle(), createRes.getTitle());
        assertEquals(postRes.getContent(), createRes.getContent());
        assertArrayEquals(postRes.getHashtags(), createRes.getHashtags());
        //????????? ??????
        assertEquals(postRes.getViewCount(), view + 1);
        postRes = postService.getPost(createRes.getId());
        assertEquals(postRes.getViewCount(), view + 1);
    }
}