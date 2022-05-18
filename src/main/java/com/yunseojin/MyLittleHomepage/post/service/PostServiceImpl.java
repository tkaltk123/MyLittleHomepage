package com.yunseojin.MyLittleHomepage.post.service;

import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.dto.PostResponse;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.mapper.PostMapper;
import com.yunseojin.MyLittleHomepage.post.mapper.SimplePostMapper;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import com.yunseojin.MyLittleHomepage.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    @Resource
    private MemberInfo memberInfo;

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Override
    public PostResponse createPost(Long boardId, PostRequest postRequest) {
        SessionUtil.checkLogin(memberInfo, true);
        var member = memberRepository.getMember(memberInfo.getId());
        var board = boardRepository.getBoard(boardId);
        var post = PostMapper.INSTANCE.toPostEntity(postRequest);
        post.setBoard(board);
        post.setWriter(member);
        if (postRequest.getHashTags() != null)
            post.setHashtags(postRequest.getHashTags());
        board.createPost();
        postRepository.save(post);
        return PostMapper.INSTANCE.toPostResponse(post);
    }

    @Override
    public PostResponse updatePost(Long postId, PostRequest postRequest) {
        SessionUtil.checkLogin(memberInfo, true);
        var member = memberRepository.getMember(memberInfo.getId());
        var post = postRepository.getPost(postId);
        checkPostWriter(post, member);
        post.update(postRequest);
        return PostMapper.INSTANCE.toPostResponse(post);
    }

    @Override
    public void deletePost(Long postId) {
        SessionUtil.checkLogin(memberInfo, true);
        var member = memberRepository.getMember(memberInfo.getId());
        var post = postRepository.getPost(postId);
        var board = post.getBoard();
        checkPostWriter(post, member);
        postRepository.delete(post);
        board.deletePost();
    }

    @Override
    public PostResponse getPost(Long postId) {
        var post = postRepository.getPost(postId);
        return PostMapper.INSTANCE.toPostResponse(post);
    }

    @Override
    public List<PostResponse> getPostList(Long boardId, Integer page) {
        var pageable = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "id"));
        var board = boardRepository.getBoard(boardId);
        var postPage = postRepository.findByBoard(board, pageable);
        if (page != 0 && postPage.isEmpty())
            throw new BadRequestException(ErrorMessage.PAGE_OUT_OF_RANGE_EXCEPTION);
        return SimplePostMapper.INSTANCE.toPostResponseList(postPage.toList());
    }

    //게시글의 작성자가 입력한 회원이 맞는지 확인
    private void checkPostWriter(PostEntity post, MemberEntity member) {
        if (post.getWriter() != member)
            throw new BadRequestException(ErrorMessage.NOT_WRITER_EXCEPTION);
    }
}
