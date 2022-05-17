package com.yunseojin.MyLittleHomepage.post.repository;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.MemberType;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void test(){
        var board = BoardEntity.builder().name("게시판").build();
        var member = MemberEntity.builder().loginId("abc").password("1234").nickname("abc").memberType(MemberType.NORMAL).build();
        var post = PostEntity.builder()
                .board(board)
                .writer(member)
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);
        var dbPost = postRepository.findById(post.getId()).get();
        postRepository.flush();
        post.setLikeCount(post.getLikeCount()+1);
        post.setTitle("1234");
        postRepository.save(post);
    }

}