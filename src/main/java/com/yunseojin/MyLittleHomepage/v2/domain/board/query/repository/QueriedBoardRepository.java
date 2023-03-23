package com.yunseojin.MyLittleHomepage.v2.domain.board.query.repository;

import com.yunseojin.MyLittleHomepage.v2.domain.board.query.model.QueriedBoard;
import com.yunseojin.MyLittleHomepage.v2.domain.contract.query.repository.ReadOnlyRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QueriedBoardRepository extends ReadOnlyRepository<QueriedBoard> {

    boolean existsByName(String name);

    @Query("select b from QueriedBoard b join fetch b.boardCount")
    List<QueriedBoard> findAllWithCount();
}