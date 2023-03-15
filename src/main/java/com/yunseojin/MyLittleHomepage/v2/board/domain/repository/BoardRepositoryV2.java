package com.yunseojin.MyLittleHomepage.v2.board.domain.repository;

import com.yunseojin.MyLittleHomepage.v2.board.domain.model.Board;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Validated
@Repository
public interface BoardRepositoryV2 extends JpaRepository<Board, Long> {

    @Query("select b from Board b join fetch b.boardCount")
    List<Board> findAllWithCount();
}