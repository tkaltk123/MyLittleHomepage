package com.yunseojin.MyLittleHomepage.v2.domain.board.command.model;

import com.yunseojin.MyLittleHomepage.domain.board.query.repository.QueriedBoardRepository;
import com.yunseojin.MyLittleHomepage.domain.member.command.model.MemberService;
import com.yunseojin.MyLittleHomepage.domain.member.command.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.domain.member.query.model.QueriedMember;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = AutowireMode.ALL)
class BoardServiceTest {

    private final MemberService service;

    private final MemberRepository repository;

    private final QueriedBoardRepository repository2;

    @Test
    void create() {
        QueriedMember memberInfo = QueriedMember.builder()
                .username("asdasd")
                .password("asdasd12")
                .nickname("asdasd")
                .build();
        var board = service.create(memberInfo);
        repository.save(board);
        repository.flush();
        var c = repository2.getById(board.getId());
        var q = c.getBoardCount();
        var d = board.readOnly();
    }
}