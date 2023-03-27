package com.yunseojin.MyLittleHomepage.application.post.service.command;

import com.yunseojin.MyLittleHomepage.application.contract.service.ApplicationService;
import com.yunseojin.MyLittleHomepage.application.post.dto.command.CreatePostCommand;
import com.yunseojin.MyLittleHomepage.domain.board.command.model.Board;
import com.yunseojin.MyLittleHomepage.domain.board.command.model.BoardService;
import com.yunseojin.MyLittleHomepage.domain.board.command.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.domain.board.query.model.QueriedBoard;
import com.yunseojin.MyLittleHomepage.domain.member.command.model.Member;
import com.yunseojin.MyLittleHomepage.domain.member.command.model.MemberService;
import com.yunseojin.MyLittleHomepage.domain.member.command.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.domain.member.query.model.QueriedMember;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = AutowireMode.ALL)
class CreatePostCommandHandlerTest {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final ApplicationService applicationService;
    private QueriedMember member;
    private Long boardId;

    @BeforeEach
    void setUp() {
        member = createMember().readOnly();
        boardId = createBoard().getId();
    }

    private Member createMember() {
        var member = QueriedMember.builder()
                .username("test12")
                .password("password")
                .nickname("test12")
                .build();
        return memberRepository.save(memberService.create(member));
    }

    private Board createBoard() {
        var board = QueriedBoard.builder()
                .name("board")
                .build();
        return boardRepository.save(boardService.create(board));
    }

    @Test
    void asd() {
        CreatePostCommand command = new CreatePostCommand();
        command.setBoardId(boardId);
        command.setMember(member);
        command.setTitle("title");
        command.setContent("content");
        applicationService.executeCommand(command);
    }

}