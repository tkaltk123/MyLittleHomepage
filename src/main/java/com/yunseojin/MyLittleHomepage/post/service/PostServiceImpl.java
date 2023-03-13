package com.yunseojin.MyLittleHomepage.post.service;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.service.InternalBoardService;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.enums.PostOrderType;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.etc.service.RedisService;
import com.yunseojin.MyLittleHomepage.post.dto.FullPostSearch;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.dto.PostResponse;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.mapper.PostMapper;
import com.yunseojin.MyLittleHomepage.post.mapper.SimplePostMapper;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;
import com.yunseojin.MyLittleHomepage.v2.member.domain.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final RedisService redisService;
    private final MemberRepository memberService;
    private final InternalBoardService boardService;
    private final InternalPostService postService;

    @Transactional
    @Override
    public PostResponse createPost(Long memberId, Long boardId, PostRequest postRequest) {

        var board = boardService.getBoardById(boardId);
        var writer = memberService.getById(memberId);
        var post = createPost(writer, board, postRequest);

        return PostMapper.INSTANCE.toPostResponse(post);
    }

    @Transactional
    @Override
    public PostResponse updatePost(Long memberId, Long postId, PostRequest postRequest) {

        var writer = memberService.getById(memberId);
        var post = postService.getPostById(postId);

        checkPostWriter(post, writer);
        post.update(postRequest);

        return PostMapper.INSTANCE.toPostResponse(post);
    }

    @Transactional
    @Override
    public void deletePost(Long memberId, Long postId) {

        var writer = memberService.getById(memberId);
        var post = postService.getPostById(postId);

        checkPostWriter(post, writer);
        deletePost(post);
    }

    @Override
    public PostResponse getPost(Long postId) {

        return PostMapper.INSTANCE.toPostResponse(postService.getPostById(postId));
    }

    @Transactional
    @Override
    public void viewPost(String ip, Long postId) {

        if (redisService.viewPost(ip, postId))
            postService.increaseViewCount(postId);
    }

    @Override
    public Page<PostResponse> getPostList(Long boardId, int postCount, PostSearch postSearch) {

        var board = boardService.getBoardById(boardId);
        var pageable = PageRequest.of(postSearch.getPage(), postCount);
        var postPage = postRepository.getPosts(board, pageable, postSearch);

        return postPage.map(SimplePostMapper.INSTANCE::toPostResponse);
    }

    @Override
    public Page<PostResponse> getPostListWithCursor(Long lastPostId, int postCount, FullPostSearch postSearch) {

        var pageable = PageRequest.of(0, postCount);
        var postPage = postRepository.getPostsWithCursor(lastPostId, pageable, postSearch);

        return postPage.map(SimplePostMapper.INSTANCE::toPostResponse);
    }

    @Override
    public List<PostResponse> getOrderedPostList(Long boardId, int postCount, PostOrderType postOrderType) {

        var board = boardService.getBoardById(boardId);
        var postPage = postRepository.getPostsOrderedBy(board, postCount, postOrderType);

        return postPage.stream().map(SimplePostMapper.INSTANCE::toPostResponse).collect(Collectors.toList());
    }

    //게시글의 작성자가 입력한 회원이 맞는지 확인
    private void checkPostWriter(PostEntity post, Member writer) {

        if (post.getWriter() != writer) {
            throw new BadRequestException(ErrorMessage.NOT_WRITER_EXCEPTION);
        }
    }

    private PostEntity createPost(Member writer, BoardEntity board, PostRequest postRequest) {

        var post = PostMapper.INSTANCE.toPostEntity(postRequest)
                .toBuilder()
                .board(board)
                .writer(writer)
                .writerName(writer.getNickname())
                .build()
                .withPostCount();

        if (postRequest.getHashTags() != null)
            post.setHashtags(postRequest.getHashTags());

        if (!redisService.createPost(writer.getId()))
            throw new BadRequestException(ErrorMessage.POST_REPEAT_EXCEPTION);

        post = postRepository.save(post);
        boardService.increasePostCount(board);

        return post;
    }

    private void deletePost(PostEntity post) {

        boardService.decreasePostCount(post.getBoard());
        postRepository.delete(post);
        post.setIsDeleted(1);
    }
}
