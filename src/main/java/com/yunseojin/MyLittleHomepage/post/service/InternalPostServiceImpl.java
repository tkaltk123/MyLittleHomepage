package com.yunseojin.MyLittleHomepage.post.service;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class InternalPostServiceImpl implements InternalPostService {

    private final PostRepository postRepository;

    @Transactional
    @Override
    public void increaseLikeCount(Long evaluableId) {

        var post = getPostById(evaluableId);
        post.getPostCount().setLikeCount(postRepository.getLikeCount(evaluableId) + 1);
    }

    @Transactional
    @Override
    public void decreaseLikeCount(Long evaluableId) {

        var post = getPostById(evaluableId);
        post.getPostCount().setLikeCount(postRepository.getLikeCount(evaluableId) - 1);
    }

    @Transactional
    @Override
    public void increaseDislikeCount(Long evaluableId) {

        var post = getPostById(evaluableId);
        post.getPostCount().setDislikeCount(postRepository.getDislikeCount(evaluableId) + 1);
    }

    @Transactional
    @Override
    public void decreaseDislikeCount(Long evaluableId) {

        var post = getPostById(evaluableId);
        post.getPostCount().setDislikeCount(postRepository.getDislikeCount(evaluableId) - 1);
    }

    @Transactional
    @Override
    public void increaseViewCount(Long postId) {

        var post = getPostById(postId);
        post.getPostCount().setViewCount(postRepository.getViewCount(postId) + 1);
    }

    @Transactional
    @Override
    public void decreaseViewCount(Long postId) {

        var post = getPostById(postId);
        post.getPostCount().setViewCount(postRepository.getViewCount(postId) - 1);
    }

    @Transactional
    @Override
    public void increaseCommentCount(Long postId) {

        var post = getPostById(postId);
        post.getPostCount().setCommentCount(postRepository.getCommentCount(postId) + 1);
    }

    @Transactional
    @Override
    public void decreaseCommentCount(Long postId) {

        var post = getPostById(postId);
        post.getPostCount().setCommentCount(postRepository.getCommentCount(postId) - 1);
    }

    @Override
    public PostEntity getPostById(Long postId) {

        var optPost = postRepository.findById(postId);

        if (optPost.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_POST_EXCEPTION);

        return optPost.get();
    }
}
