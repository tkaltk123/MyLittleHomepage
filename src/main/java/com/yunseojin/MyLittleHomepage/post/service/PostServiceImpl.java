package com.yunseojin.MyLittleHomepage.post.service;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.enums.PostOrderType;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.etc.service.RedisService;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.dto.PostResponse;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.mapper.PostMapper;
import com.yunseojin.MyLittleHomepage.post.mapper.SimplePostMapper;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    private final RedisService redisService;

    @Override
    public PostResponse createPost(Long memberId, Long boardId, PostRequest postRequest) {

        var board = getBoardById(boardId);
        var writer = getMemberById(memberId);
        var post = PostMapper.INSTANCE.toPostEntity(postRequest)
                .toBuilder()
                .board(board)
                .writer(writer)
                .writerName(writer.getNickname())
                .build();

        if (postRequest.getHashTags() != null)
            post.setHashtags(postRequest.getHashTags());

        createPost(memberId, board, post);

        return PostMapper.INSTANCE.toPostResponse(post);
    }

    @Override
    public PostResponse updatePost(Long memberId, Long postId, PostRequest postRequest) {

        var writer = getMemberById(memberId);
        var post = getPostById(postId);

        checkPostWriter(post, writer);
        post.update(postRequest);

        return PostMapper.INSTANCE.toPostResponse(post);
    }

    @Override
    public void deletePost(Long memberId, Long postId) {

        var writer = getMemberById(memberId);
        var post = getPostById(postId);

        checkPostWriter(post, writer);
        deletePost(post);
    }

    @Override
    public PostResponse getPost(Long postId) {

        return PostMapper.INSTANCE.toPostResponse(getPostById(postId));
    }

    @Override
    public void viewPost(String ip, Long postId) {

        var post = getPostById(postId);

        if (redisService.viewPost(ip, postId))
            post.increaseViewCount();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> getPostList(Long boardId, int postCount, PostSearch postSearch) {

        var pageable = PageRequest.of(postSearch.getPage(), postCount);
        var board = getBoardById(boardId);
        var postPage = postRepository.getPosts(board, pageable, postSearch);

        return postPage.map(SimplePostMapper.INSTANCE::toPostResponse);
    }

    @Override
    public List<PostResponse> getOrderedPostList(Long boardId, int postCount, PostOrderType postOrderType) {

        var board = getBoardById(boardId);
        var postPage = postRepository.getPostsOrderedBy(board, postCount, postOrderType);

        return postPage.stream().map(SimplePostMapper.INSTANCE::toPostResponse).collect(Collectors.toList());
    }

    private MemberEntity getMemberById(Long memberId) {

        var optMember = memberRepository.findById(memberId);

        if (optMember.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION);

        return optMember.get();
    }

    private BoardEntity getBoardById(Long boardId) {

        var optBoard = boardRepository.findById(boardId);

        if (optBoard.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_BOARD_EXCEPTION);

        return optBoard.get();
    }

    private PostEntity getPostById(Long postId) {

        var optPost = postRepository.findById(postId);

        if (optPost.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_POST_EXCEPTION);

        return optPost.get();
    }

    //게시글의 작성자가 입력한 회원이 맞는지 확인
    private void checkPostWriter(PostEntity post, MemberEntity writer) {

        if (post.getWriter() != writer)
            throw new BadRequestException(ErrorMessage.NOT_WRITER_EXCEPTION);
    }

    private void createPost(Long memberId, BoardEntity board, PostEntity post) {

        if (!redisService.createPost(memberId))
            throw new BadRequestException(ErrorMessage.POST_REPEAT_EXCEPTION);
        postRepository.save(post);
        board.increasePostCount();
    }

    private void deletePost(PostEntity post) {

        post.getBoard().decreasePostCount();
        postRepository.delete(post);
        post.setIsDeleted(1);
    }
}
