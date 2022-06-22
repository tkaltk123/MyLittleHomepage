package com.yunseojin.MyLittleHomepage.board.repository;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    Optional<BoardEntity> findByName(String name);
}