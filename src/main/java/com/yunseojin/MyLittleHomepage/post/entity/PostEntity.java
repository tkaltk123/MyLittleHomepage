package com.yunseojin.MyLittleHomepage.post.entity;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.hashtag.entity.HashtagEntity;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@SQLDelete(sql = "UPDATE posts SET is_deleted = 1 WHERE id=?")
@Where(clause = "is_deleted = 0")
@Table(name = "posts")
@Cacheable
public class PostEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "board_id", nullable = false)
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "writer_id", nullable = false)
    private MemberEntity writer;

    @Column(name = "writer_name", nullable = false)
    private String writerName;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content")
    private String content;


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "post", optional = false, cascade = CascadeType.PERSIST)
    private PostCount postCount;

    @Builder.Default
    @OrderBy("id asc")
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HashtagEntity> hashtags = new ArrayList<>();

    public PostEntity withPostCount() {

        postCount = new PostCount();
        postCount.setPost(this);
        return this;
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
        if (hashTags == null)
            return;
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