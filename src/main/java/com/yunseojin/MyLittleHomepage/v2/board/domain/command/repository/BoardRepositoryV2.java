package com.yunseojin.MyLittleHomepage.v2.board.domain.command.repository;

import com.yunseojin.MyLittleHomepage.v2.board.domain.command.aggregate.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepositoryV2 extends JpaRepository<Board, Long> {

}