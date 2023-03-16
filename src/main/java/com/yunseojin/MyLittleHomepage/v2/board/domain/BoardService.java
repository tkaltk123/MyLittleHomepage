package com.yunseojin.MyLittleHomepage.v2.board.domain;

import com.yunseojin.MyLittleHomepage.v2.board.domain.repository.BoardRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepositoryV2 repository;
}
