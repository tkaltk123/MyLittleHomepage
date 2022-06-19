package com.yunseojin.MyLittleHomepage.etc.enums;

public enum EvaluationType {

    NONE, LIKE, DISLIKE;

    public EvaluationType opposite() {

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
