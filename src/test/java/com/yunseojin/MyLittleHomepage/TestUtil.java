package com.yunseojin.MyLittleHomepage;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.enums.MemberType;
import com.yunseojin.MyLittleHomepage.etc.exception.BaseException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestUtil {

    public static void assertError(ErrorMessage error, Executable executable) {
        assertEquals(error.getCode(), assertThrows(BaseException.class, executable).getCode());
    }

    public static MemberEntity createTestMember(String loginId, String nickname) {

        return MemberEntity.builder()
                .loginId(loginId)
                .password("1234")
                .nickname(nickname)
                .memberType(MemberType.NORMAL)
                .build()
                .withHashingPassword();
    }

    public static MemberRequest createMemberRequest(String loginId, String nickname,String currentPassword){

        return MemberRequest.builder()
                .loginId(loginId)
                .nickname(nickname)
                .password("1234")
                .currentPassword(currentPassword)
                .build();
    }

    public static BoardEntity createTestBoard(String name) {

        return BoardEntity.builder().
                name(name)
                .build()
                .withBoardCount();
    }

    public static PostEntity createTestPost(MemberEntity member, BoardEntity board, String content) {

        return PostEntity.builder()
                .writer(member)
                .writerName(member.getNickname())
                .board(board)
                .title("제목")
                .content(content)
                .build()
                .withPostCount();
    }

    public static PostRequest createPostRequest(String content) {

        return PostRequest.builder()
                .title("제목")
                .content(content)
                .hashTags(new String[]{"태그1", "태그2"})
                .build();
    }

    public static CommentEntity createTestComment(MemberEntity member, PostEntity post, String content) {

        return CommentEntity.builder()
                .writer(member)
                .writerName(member.getNickname())
                .post(post)
                .content(content)
                .build()
                .withCommentCount();
    }

    public static CommentRequest createCommentRequest(Long parentId, Long commentId, String content) {

        return CommentRequest.builder()
                .parentId(parentId)
                .commentId(commentId)
                .content(content)
                .build();
    }
}
