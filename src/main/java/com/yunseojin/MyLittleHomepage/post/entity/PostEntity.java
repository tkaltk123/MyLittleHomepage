package com.yunseojin.MyLittleHomepage.post.entity;

import com.yunseojin.MyLittleHomepage.board.entity.BoardCount;
import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.Evaluable;
import com.yunseojin.MyLittleHomepage.hashtag.entity.HashtagEntity;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@SQLDelete(sql = "UPDATE posts SET is_deleted = 1 WHERE id=?")
@Where(clause = "is_deleted = 0")
@Table(name = "posts")
public class PostEntity extends BaseEntity implements Evaluable {
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
        @JoinColumn(name = "board_id", nullable = false)
    private BoardEntity board;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "writer_id", nullable = false)
    private MemberEntity writer;

    @Setter
    @Column(name = "writer_name", nullable = false)
    private String writerName;

    @Setter
    @Column(name = "title", nullable = false)
    private String title;

    @Setter
    @Lob
    @Column(name = "content")
    private String content;

    //즉시 로딩
    @OneToOne(mappedBy = "post", optional = false, cascade = CascadeType.PERSIST)
    private PostCount postCount;

    @Builder.Default
    @OrderBy("id asc")
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HashtagEntity> hashtags = new ArrayList<>();

    public void setPostCount(PostCount postCount) {
        if (postCount != null)
            postCount.setPost(this);
        this.postCount = postCount;
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

    public Integer getCommentCount() {
        return postCount.getCommentCount();
    }

    public void increaseCommentCount() {
        postCount.increaseCommentCount();
    }

    public void decreaseCommentCount() {
        postCount.decreaseCommentCount();
    }

    public Integer getViewCount() {
        return postCount.getViewCount();
    }

    public void increaseViewCount() {
        postCount.increaseViewCount();
    }

    public Integer getLikeCount() {
        return postCount.getLikeCount();
    }

    @Override
    public void increaseLikeCount() {
        postCount.increaseLikeCount();
    }

    @Override
    public void decreaseLikeCount() {
        postCount.decreaseLikeCount();
    }

    public Integer getDislikeCount() {
        return postCount.getDislikeCount();
    }

    @Override
    public void increaseDislikeCount() {
        postCount.increaseDislikeCount();
    }

    @Override
    public void decreaseDislikeCount() {
        postCount.decreaseDislikeCount();
    }
}