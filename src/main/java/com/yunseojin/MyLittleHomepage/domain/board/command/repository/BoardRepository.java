package com.yunseojin.MyLittleHomepage.domain.board.command.repository;

import com.yunseojin.MyLittleHomepage.domain.board.command.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}