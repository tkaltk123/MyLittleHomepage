package com.yunseojin.MyLittleHomepage.board.repository;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    BoardEntity findByName(String name);

    default BoardEntity getBoard(Long boardId){
        var _board = findById(boardId);
        if (_board.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_BOARD_EXCEPTION);
        return _board.get();
    }
}