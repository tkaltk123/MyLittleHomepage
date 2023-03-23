package com.yunseojin.MyLittleHomepage.v2.domain.feedback.vo;

public enum FeedbackType {

    NONE, LIKE, DISLIKE;

    public FeedbackType reverse() {

        switch (this) {

            case LIKE:
                return DISLIKE;

            case DISLIKE:
                return LIKE;

            default:
                return NONE;
        }
    }
}
