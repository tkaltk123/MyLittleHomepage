package com.yunseojin.MyLittleHomepage.v2.board.domain.query.repository;

import com.yunseojin.MyLittleHomepage.v2.board.domain.query.entity.QueriedBoard;
import com.yunseojin.MyLittleHomepage.v2.contract.domain.query.repository.ReadOnlyRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QueriedBoardRepository extends ReadOnlyRepository<QueriedBoard> {

    boolean existsByName(String name);

    @Query("select b from QueriedBoard b join fetch b.boardCount")
    List<QueriedBoard> findAllWithCount();
}