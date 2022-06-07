package com.yunseojin.MyLittleHomepage.board.mapper;

import com.yunseojin.MyLittleHomepage.board.dto.BoardResponse;
import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.repository.BoardRepository;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentResponse;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface BoardMapper {
    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    @Mapping(target = "postCount", source = "boardCount.postCount")
    BoardResponse toPostResponse(BoardEntity board);

    List<BoardResponse> toPostResponseList(List<BoardEntity> boards);


}
