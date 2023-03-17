package com.yunseojin.MyLittleHomepage.v2.board.domain.query.repository;

import com.yunseojin.MyLittleHomepage.v2.board.domain.query.entity.QueriedBoard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Validated
@Repository
public interface QueriedBoardRepository extends JpaRepository<QueriedBoard, Long> {

    @Query("select b from QueriedBoard b join fetch b.boardCount")
    List<QueriedBoard> findAllWithCount();
}