package com.yunseojin.MyLittleHomepage.board.repository;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Query("select b from BoardEntity b " +
            "join fetch b.boardCount " +
            "where b.name = :name")
    BoardEntity findByName(@Param("name") String name);

    @Query("select b from BoardEntity b " +
            "join fetch b.boardCount " +
            "where b.id = :boardId")
    BoardEntity findByIdWithJoin(@Param("boardId") Long boardId);

    default BoardEntity getBoard(Long boardId) {
        var board = findByIdWithJoin(boardId);
        if (board == null)
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_BOARD_EXCEPTION);
        return board;
    }
}