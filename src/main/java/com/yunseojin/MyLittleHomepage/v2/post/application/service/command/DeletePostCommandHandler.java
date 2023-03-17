package com.yunseojin.MyLittleHomepage.v2.post.application.service.command;

import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.command.DeletePostCommand;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.aggregete.PostService;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.repository.PostRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class DeletePostCommandHandler implements CommandHandler<DeletePostCommand, Void> {

    private final PostService service;

    private final PostRepositoryV2 repository;
    private final MemberRepository memberRepository;

    @Override
    public Void handle(DeletePostCommand command) {
        var post = repository.getById(command.getPostId());
        var member = memberRepository.getById(command.getMemberId());
        service.delete(post, member);
        repository.delete(post);
        return null;
    }
}
