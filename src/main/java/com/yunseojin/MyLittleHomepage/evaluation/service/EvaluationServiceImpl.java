package com.yunseojin.MyLittleHomepage.evaluation.service;

import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@RequiredArgsConstructor
@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private MemberInfo memberInfo;

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;


    @Override
    public String likePost(Long postId) {
        return null;
    }

    @Override
    public String likeComment(Long commentId) {
        return null;
    }

    @Override
    public String dislikePost(Long postId) {
        return null;
    }

    @Override
    public String dislikeComment(Long commentId) {
        return null;
    }
}
