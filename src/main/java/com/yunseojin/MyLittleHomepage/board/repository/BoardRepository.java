package com.yunseojin.MyLittleHomepage.board.repository;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    Optional<BoardEntity> findByName(String name);

    @Query("select b from BoardEntity b join fetch b.boardCount")
    List<BoardEntity> findAllWithCount();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select bc.postCount from BoardCount bc where bc.id = :boardId ")
    Integer getPostCount(@Param("boardId") Long boardId);
}