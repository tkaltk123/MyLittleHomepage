package com.yunseojin.MyLittleHomepage.evaluation.service;

public interface EvaluableService {

    void increaseLikeCount(Long evaluableId);

    void decreaseLikeCount(Long evaluableId);

    void increaseDislikeCount(Long evaluableId);

    void decreaseDislikeCount(Long evaluableId);
}
