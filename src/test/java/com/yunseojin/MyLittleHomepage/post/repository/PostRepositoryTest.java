package com.yunseojin.MyLittleHomepage.post.repository;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.MemberType;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void test() {
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
        post.increaseLikeCount();
        post.setTitle("1234");
        postRepository.save(post);
    }

    @Test
    public void findByWriter() {
        var board = BoardEntity.builder().name("게시판").build();
        var member = MemberEntity.builder().loginId("abc").password("1234").nickname("abc").memberType(MemberType.NORMAL).build();
        var post = PostEntity.builder()
                .board(board)
                .writer(member)
                .title("제목")
                .content("내용")
                .build();
        var post2 = PostEntity.builder()
                .board(board)
                .writer(member)
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);
        postRepository.save(post2);
        PageRequest req;
        req = PageRequest.of(1, 1, Sort.by(Sort.Direction.DESC, "id"));
        var lists = postRepository.getPosts(board, req);

    }

}