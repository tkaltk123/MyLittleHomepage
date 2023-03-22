package com.yunseojin.MyLittleHomepage.v2.board.domain.command.aggregate;


import com.yunseojin.MyLittleHomepage.v2.board.domain.command.event.BoardCreatedEvent;
import com.yunseojin.MyLittleHomepage.v2.board.domain.command.event.BoardDeletedEvent;
import com.yunseojin.MyLittleHomepage.v2.board.domain.command.event.BoardUpdatedEvent;
import com.yunseojin.MyLittleHomepage.v2.board.domain.command.validation.name.UniqueName;
import com.yunseojin.MyLittleHomepage.v2.board.domain.query.entity.QueriedBoard;
import com.yunseojin.MyLittleHomepage.v2.contract.domain.command.aggregate.BaseAggregateRoot;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Where(clause = "is_deleted = 0")
@SQLDelete(sql = "UPDATE boards SET is_deleted = 1 WHERE id=?")
@Table(name = "boards")
public class Board extends BaseAggregateRoot<Board> {

    @NotNull
    @UniqueName
    @Size(min = 2, max = 50, message = "게시판 이름은 2~50글자 입니다.")
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @OneToOne(mappedBy = "board", cascade = CascadeType.PERSIST)
    private BoardCountV2 boardCount;

    protected Board(QueriedBoard boardInfo) {
        this.name = boardInfo.getName();
        setBoardCount(new BoardCountV2());
        registerEvent(new BoardCreatedEvent(readOnly()));
    }

    private void setBoardCount(BoardCountV2 boardCount) {
        this.boardCount = boardCount;
        this.boardCount.setBoard(this);
    }

    protected Board update(QueriedBoard boardInfo) {

        updateName(boardInfo.getName());
        registerEvent(new BoardUpdatedEvent(readOnly()));
        return this;
    }

    private void updateName(String name) {
        if (name != null && !name.equals(this.name)) {
            this.name = name;
        }
    }

    @Override
    protected void delete() {
        super.delete();
        registerEvent(new BoardDeletedEvent(readOnly()));
    }

    public QueriedBoard readOnly() {
        return BoardConverter.INSTANCE.readOnly(this);
    }

    @Mapper
    interface BoardConverter {

        BoardConverter INSTANCE = Mappers.getMapper(BoardConverter.class);

        QueriedBoard readOnly(Board board);
    }
}