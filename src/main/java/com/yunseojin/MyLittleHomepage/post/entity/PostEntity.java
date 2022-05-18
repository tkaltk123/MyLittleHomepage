package com.yunseojin.MyLittleHomepage.post.entity;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.CommentEvaluationEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.PostEvaluationEntity;
import com.yunseojin.MyLittleHomepage.hashtag.entity.HashtagEntity;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@SQLDelete(sql = "UPDATE POSTS SET IS_DELETED = 1 WHERE ID=?")
@Where(clause = "IS_DELETED = 0")
@Table(name = "POSTS")
@SecondaryTable(name = "POST_COUNTS", pkJoinColumns = @PrimaryKeyJoinColumn(name = "POST_ID"))
public class PostEntity extends BaseEntity {
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BOARD_ID", nullable = false)
    private BoardEntity board;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "WRITER_ID", nullable = false)
    private MemberEntity writer;

    @Setter
    @Column(name = "TITLE", nullable = false)
    private String title;

    @Setter
    @Lob
    @Column(name = "CONTENT")
    private String content;

    @Basic
    @Builder.Default
    @Column(name = "VIEW_COUNT", table = "POST_COUNTS", nullable = false)
    private Integer viewCount = 0;

    @Basic
    @Builder.Default
    @Column(name = "COMMENT_COUNT", table = "POST_COUNTS", nullable = false)
    private Integer commentCount = 0;

    @Basic
    @Builder.Default
    @Column(name = "LIKE_COUNT", table = "POST_COUNTS", nullable = false)
    private Integer likeCount = 0;

    @Basic
    @Builder.Default
    @Column(name = "DISLIKE_COUNT", table = "POST_COUNTS", nullable = false)
    private Integer dislikeCount = 0;

    @Builder.Default
    @OrderBy("id asc")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    @Fetch(FetchMode.SUBSELECT)
    @Builder.Default
    @OrderBy("id asc")
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HashtagEntity> hashtags = new ArrayList<>();

    @Builder.Default
    @OrderBy("id asc")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostEvaluationEntity> evaluations = new ArrayList<>();

    public void setBoard(BoardEntity board) {
        if (this.board != null)
            this.board.getPosts().remove(this);
        this.board = board;
        if (board != null) {
            board.getPosts().add(this);
        }
    }

    public void setWriter(MemberEntity writer) {
        if (this.writer != null)
            this.writer.getPosts().remove(this);
        this.writer = writer;
        if (writer != null) {
            writer.getPosts().add(this);
        }
    }

    public void increaseCommentCount() {
        ++this.commentCount;
    }

    public void decreaseCommentCount() {
        --this.commentCount;
    }

    public void increaseLikeCount() {
        ++this.likeCount;
    }

    public void decreaseLikeCount() {
        --this.likeCount;
    }

    public void increaseDislikeCount() {
        ++this.dislikeCount;
    }

    public void decreaseDislikeCount() {
        --this.dislikeCount;
    }

    public void increaseViewCount() {
        ++this.viewCount;
    }

    public void decreaseViewCount() {
        --this.viewCount;
    }

    public String[] getStringHashtags() {
        return hashtags.stream().map(HashtagEntity::toString).toArray(String[]::new);
    }

    public void setHashtags(String... hashtags) {
        for (var tag : hashtags) {
            this.hashtags.add(toHashTag(tag));
        }
    }

    public void update(PostRequest postRequest) {
        title = postRequest.getTitle();
        content = postRequest.getContent();
        updateHashTag(postRequest.getHashTags());
    }

    private HashtagEntity toHashTag(String tag) {
        return HashtagEntity.builder().post(this).tag(tag).build();
    }

    private void updateHashTag(String[] hashTags) {
        var newTags = Arrays.stream(hashTags).collect(Collectors.toSet());
        for (var hashTag : List.copyOf(hashtags)) {
            var tag = hashTag.getTag();
            if (newTags.contains(tag))
                newTags.remove(tag);
            else
                hashtags.remove(hashTag);
        }
        for (var tag : newTags) {
            hashtags.add(toHashTag(tag));
        }
    }
}