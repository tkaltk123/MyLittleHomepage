package com.yunseojin.MyLittleHomepage.v2.post.application.service.command;

import com.yunseojin.MyLittleHomepage.v2.auth.service.JwtTokenProvider;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.member.application.mapper.MemberMapper;
import com.yunseojin.MyLittleHomepage.v2.member.domain.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.v2.member.domain.service.MemberService;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.command.CreatePostCommand;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CreatePostCommandHandler implements CommandHandler<CreatePostCommand, PostResponse> {

    private final MemberService service;

    private final MemberRepository repository;

    private final MemberMapper mapper;

    private final JwtTokenProvider tokenProvider;

    @Override
    public PostResponse handle(CreatePostCommand command) {
        return null;
    }
}
