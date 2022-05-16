package com.yunseojin.MyLittleHomepage.board.entity;

import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@SQLDelete(sql = "UPDATE BOARDS SET IS_DELETED = 1 WHERE ID=?")
@Where(clause = "IS_DELETED = 0")
@Table(name = "BOARDS")
@SecondaryTable(name = "BOARD_COUNTS", pkJoinColumns = @PrimaryKeyJoinColumn(name = "BOARD_ID"))
public class BoardEntity extends BaseEntity {
    @Basic
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Basic
    @Builder.Default
    @Column(name = "POST_COUNT", table = "BOARD_COUNTS", nullable = false)
    private Integer postCount = 0;

    @Builder.Default
    @OrderBy("id desc")
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostEntity> posts = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void createPost() {
        postCount += 1;
    }

    public void deletePost() {
        postCount -= 1;
    }
}