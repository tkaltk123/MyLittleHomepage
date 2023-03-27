package com.yunseojin.MyLittleHomepage.domain.board.command.repository;

import com.yunseojin.MyLittleHomepage.domain.board.command.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Modifying
    @Query("update BoardCount bc set bc.postCount = bc.postCount + 1 where bc.id = :boardId")
    void increasePostCount(@Param("boardId") Long boardId);

    @Modifying
    @Query("update BoardCount bc set bc.postCount = bc.postCount - 1 where bc.id = :boardId")
    void decreasePostCount(@Param("boardId") Long boardId);
}